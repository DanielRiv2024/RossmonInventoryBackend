package com.daniel.inventory.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final EntityManager entityManager;
    private ProductRepository productRepository;

    @Autowired
    public ProductService(EntityManager entityManager, ProductRepository productRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
    }
    @Transactional
    public void createProduct(Product product) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("InsertProduct");
            storedProcedure.registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("precio", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("cantidad", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("categoria", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("creadoPor", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("actualizadoPor", Integer.class, ParameterMode.IN);
    
            storedProcedure.setParameter("nombre", product.getName());
            storedProcedure.setParameter("precio", product.getPrice());
            storedProcedure.setParameter("cantidad", product.getAmount());
            storedProcedure.setParameter("categoria", product.getCategory());
            storedProcedure.setParameter("creadoPor", product.getCreatedBy());
            storedProcedure.setParameter("actualizadoPor", product.getUpdateBy());
    
            storedProcedure.execute();
    
            System.out.println("Producto creado exitosamente");
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el producto: " + e.getMessage());
        }
    }
    

    @Transactional
    public boolean updateProduct(Product product) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("UpdateProduct");
            storedProcedure.registerStoredProcedureParameter("productId", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("productName", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("price", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("amount", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("categoryId", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("updateBy", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("status", Integer.class, ParameterMode.OUT);

            storedProcedure.setParameter("productId", product.getId());
            storedProcedure.setParameter("productName", product.getName());
            storedProcedure.setParameter("price", product.getPrice());
            storedProcedure.setParameter("amount", product.getAmount());
            storedProcedure.setParameter("categoryId", product.getCategory());
            storedProcedure.setParameter("updateBy", product.getUpdateBy());

            storedProcedure.execute();

            Integer status = (Integer) storedProcedure.getOutputParameterValue("status");

            if (status != null && status == 1) {
                System.out.println("Producto actualizado exitosamente");
                return true;
            } else {
                System.out.println("Error al actualizar el producto (ID no encontrado)");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public int deleteProduct(int productId) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("DeleteProduct");
            storedProcedure.registerStoredProcedureParameter("productId", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("status", Integer.class, ParameterMode.OUT);

            storedProcedure.setParameter("productId", productId);

            storedProcedure.execute();

            Integer status = (Integer) storedProcedure.getOutputParameterValue("status");

            if (status != null && status == 1) {
                System.out.println("Producto eliminado exitosamente");
            } else if (status != null && status == 0) {
                System.out.println("El producto no se pudo eliminar (ID no encontrado)");
            } else {
                System.out.println("Error al eliminar el producto");
            }

            return status != null ? status : -1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el producto: " + e.getMessage());
            return -1;
        }
    }

    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Transactional
    public Optional<Product> getProductById(int id) {
        return productRepository.findProductById(id);
    }
}
