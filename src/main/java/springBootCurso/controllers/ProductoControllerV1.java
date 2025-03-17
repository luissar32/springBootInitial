package springBootCurso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.exceptions.ProductNotFoundException;
import springBootCurso.service.ProductoService;

import java.util.List;

//@RestController
//@RequestMapping("/sistema/api/v1")
public class ProductoControllerV1 {

    private final ProductoService productoService;

    @Autowired
    public ProductoControllerV1(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/products")
    public List<ProductoDTO> getProducts() {
        return productoService.getProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
        try {
            ProductoDTO producto = productoService.getProductById(id);
            return ResponseEntity.ok(producto);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
