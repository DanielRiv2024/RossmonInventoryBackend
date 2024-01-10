package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Interface.ProductRepository;
import com.example.models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.springframework.jdbc.object.StoredProcedure;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        logger.info("Productos llamados", products);
        return products;
    }

    public void AddProduct(Product product) {
        logger.info("Producto Creado : {}", product);
        StoredProcedureQuery q = entityManager.createStoredProcedureQuery("INSERT_PRODUCT");

        q.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(2, Double.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);

        q.setParameter(1, product.getName());
        q.setParameter(2, product.getPrice());
        q.setParameter(3, (int) product.getAmount());
        q.setParameter(4, product.getIdCategory());
        q.setParameter(5, product.getCreatedBy());
        q.setParameter(6, product.getUpdateBy());
        q.execute();
    }
    
    public boolean updateProduct(Product product) {
    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("UPDATE_PRODUCT");

    query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(3, Double.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
     query.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT);

    query.setParameter(1, product.getIdProduct());
    query.setParameter(2, product.getName());
    query.setParameter(3, product.getPrice());
    query.setParameter(4, (int) product.getAmount());
    query.setParameter(5, product.getIdCategory());
    query.setParameter(6, product.getUpdateBy());

    query.execute();
      int result = (Integer) query.getOutputParameterValue(7);
      return result > 0;
}
    
    public boolean deleteProduct(Long id) {
    StoredProcedureQuery query = entityManager.createStoredProcedureQuery("DELETE_PRODUCT");
    query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
    query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

    query.setParameter(1, id);
    query.execute();

    int result = (Integer) query.getOutputParameterValue(2);
    return result > 0;
}


    public String getProductById(Long id) {
    Query query = entityManager.createNativeQuery("SELECT FIND_PRODUCT_BY_ID_FUNC(:id) FROM DUAL");
    query.setParameter("id", id);
    Object result = query.getSingleResult();
    return result != null ? result.toString() : null;
}



}
