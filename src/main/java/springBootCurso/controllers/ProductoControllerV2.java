package springBootCurso.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.entities.Productos;
import springBootCurso.service.ProductoService;
import java.util.List;

//@RestController
//@RequestMapping("/sistema/api/v2")
public class ProductoControllerV2 {

    private final ProductoService productoService;

    public ProductoControllerV2(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public List<Productos> getProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Productos> getProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Productos> postProducto(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.createProducto(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.updateProducto(id, productoDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        return productoService.patchProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoService.deleteProducto(id);
    }
}
