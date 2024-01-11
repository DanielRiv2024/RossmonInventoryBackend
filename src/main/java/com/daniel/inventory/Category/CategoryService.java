package com.daniel.inventory.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    private final EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createCategory(Category category) {
        try {
            System.out.println("Categoría a crear:");
            System.out.println("Name: " + category.getName());
            System.out.println("Description: " + category.getDescription());
            System.out.println("CreatedBy: " + category.getCreatedBy());
            System.out.println("UpdateBy: " + category.getUpdateBy());

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("InsertCategory");
            storedProcedure.registerStoredProcedureParameter("categoryName", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("categoryDescription", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("createdBy", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("updateBy", Integer.class, ParameterMode.IN);

            storedProcedure.setParameter("categoryName", category.getName());
            storedProcedure.setParameter("categoryDescription", category.getDescription());
            storedProcedure.setParameter("createdBy", category.getCreatedBy());
            storedProcedure.setParameter("updateBy", category.getUpdateBy());

            storedProcedure.execute();

            System.out.println("Procedimiento almacenado ejecutado");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear la categoría: " + e.getMessage());
        }
    }

    @Transactional
    public boolean updateCategory(Category category) {
        try {
            System.out.println("Categoría a editar:");
            System.out.println("ID: " + category.getId());
            System.out.println("Name: " + category.getName());
            System.out.println("Description: " + category.getDescription());
            System.out.println("UpdateBy: " + category.getUpdateBy());

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("UpdateCategory");
            storedProcedure.registerStoredProcedureParameter("categoryId", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("categoryName", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("categoryDescription", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("updateBy", Integer.class, ParameterMode.IN);

            storedProcedure.setParameter("categoryId", category.getId());
            storedProcedure.setParameter("categoryName", category.getName());
            storedProcedure.setParameter("categoryDescription", category.getDescription());
            storedProcedure.setParameter("updateBy", category.getUpdateBy());

            boolean isUpdated = (boolean) storedProcedure.getSingleResult();

            if (isUpdated) {
                System.out.println("Categoría actualizada exitosamente");
            } else {
                System.out.println("La categoría no se pudo actualizar (ID no encontrado)");
            }

            return isUpdated;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al editar la categoría: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public int deleteCategory(int categoryId) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("DeleteCategory");
            storedProcedure.registerStoredProcedureParameter("categoryId", Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("status", Integer.class, ParameterMode.OUT); // Registrar
                                                                                                          // el
                                                                                                          // parámetro
                                                                                                          // de salida
                                                                                                          // 'status'
            storedProcedure.setParameter("categoryId", categoryId);
            storedProcedure.execute();

            int status = (int) storedProcedure.getOutputParameterValue("status");
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Transactional
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();

        if (categories != null) {
            System.out.println("Categorías recuperadas exitosamente:");
            for (Category category : categories) {
                System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
            }
        } else {
            System.out.println("No se encontraron categorías.");
        }

        return categories;
    }

    @Transactional
    public Optional<Category> getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findCategoryById(id);

        if (category.isPresent()) {
            System.out.println("Categoría encontrada por ID: " + id);
            Category foundCategory = category.get();
            System.out.println("ID: " + foundCategory.getId() + ", Name: " + foundCategory.getName());
        } else {
            System.out.println("Categoría no encontrada por ID: " + id);
        }

        return category;
    }

}