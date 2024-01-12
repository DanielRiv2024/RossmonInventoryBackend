package com.daniel.inventory.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

   @PostMapping("/create")
public ResponseEntity<String> createProduct(@RequestBody Product product) {
    try {
        productService.createProduct(product);
        return ResponseEntity.ok("Producto creado exitosamente");
    } catch (Exception e) {
        String errorMessage = "Error al crear el producto: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product) {
        boolean isUpdated = productService.updateProduct(product);

        if (isUpdated) {
            return ResponseEntity.ok("Producto actualizado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar el producto (ID no encontrado)");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        int status = productService.deleteProduct(id);

        if (status == 1) {
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } else if (status == 0) {
            return ResponseEntity.badRequest().body("No se pudo eliminar el producto (ID no encontrado)");
        } else {
            return ResponseEntity.badRequest().body("Error al eliminar el producto");
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
