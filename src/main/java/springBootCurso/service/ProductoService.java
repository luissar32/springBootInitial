package springBootCurso.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springBootCurso.domain.ProductResponse;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.entities.Productos;
import springBootCurso.exceptions.ProductNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ProductMapper productMapper;

    // URL de la API falsa
    private final String API_URL = "https://fakestoreapi.com/products";

    // Constructor
    public ProductoService(ProductRepository productRepository, RestTemplate restTemplate, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.productMapper = productMapper;
    }

    // Obtener todos los productos desde la base de datos
    public List<Productos> getAllProductos() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID desde la base de datos
    public Productos getProductoById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID: " + id + " doesn't exist."));
    }

    // Crear un producto en la base de datos
    public Productos createProducto(ProductoDTO productoDTO) {
        Productos product = new Productos();
        product.setName(productoDTO.getName());
        product.setCost(productoDTO.getCost());
        product.setDetails(productoDTO.getDetails());
        product.setCategoryName(productoDTO.getCategoryName());
        product.setImageUrl(productoDTO.getImageUrl());
        return productRepository.save(product);
    }

    // Actualizar un producto en la base de datos
    public ResponseEntity<?> updateProducto(Long id, ProductoDTO productoDTO) {
        return productRepository.findById(id).map(product -> {
            product.setName(productoDTO.getName());
            product.setCost(productoDTO.getCost());
            product.setDetails(productoDTO.getDetails());
            product.setCategoryName(productoDTO.getCategoryName());
            product.setImageUrl(productoDTO.getImageUrl());
            productRepository.save(product);
            return ResponseEntity.ok("El producto " + product.getName() + " fue actualizado correctamente");
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró el producto con el ID: " + id));
    }

    // Actualizar parcialmente un producto en la base de datos
    public ResponseEntity<?> patchProducto(Long id, ProductoDTO productoDTO) {
        return productRepository.findById(id).map(product -> {
            if (productoDTO.getName() != null) {
                product.setName(productoDTO.getName());
            }
            if (productoDTO.getCost() != null) {
                product.setCost(productoDTO.getCost());
            }
            if (productoDTO.getDetails() != null) {
                product.setDetails(productoDTO.getDetails());
            }
            if (productoDTO.getCategoryName() != null) {
                product.setCategoryName(productoDTO.getCategoryName());
            }
            if (productoDTO.getImageUrl() != null) {
                product.setImageUrl(productoDTO.getImageUrl());
            }
            productRepository.save(product);
            return ResponseEntity.ok("El producto " + product.getName() + " fue actualizado correctamente");
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró el producto con el ID: " + id));
    }

    // Eliminar un producto de la base de datos
    public ResponseEntity<?> deleteProducto(Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok("El producto con ID " + id + " fue eliminado correctamente");
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró un producto con el ID: " + id));
    }

    // ==================== VERSION 2: OBTENER PRODUCTOS DESDE LA API FALSA ====================

    // Obtener todos los productos desde la API externa y mapearlos con ProductMapper
    public List<ProductoDTO> getProducts() {
        // Llamar a la API externa
        ProductResponse[] products = restTemplate.getForObject(API_URL, ProductResponse[].class);

        // Depuración: Verificar datos crudos de la API
        System.out.println("API Response: " + Arrays.toString(products));

        // Convertir la respuesta de la API a ProductoDTO usando ProductMapper
        List<ProductoDTO> productoDTOS = products != null ? Arrays.stream(products)
                .map(productMapper::productResponseToProductoDTO)
                .collect(Collectors.toList()) : List.of();

        // Depuración: Verificar el mapeo correcto
        productoDTOS.forEach(product -> System.out.println("Mapped DTO: " + product));

        return productoDTOS;
    }

    // Obtener un producto específico por ID desde la API externa
    public ProductoDTO getProductById(int id) {
        // Construimos la URL para obtener un producto por ID
        String url = API_URL + "/" + id;

        // Llamar a la API externa
        ProductResponse response = restTemplate.getForObject(url, ProductResponse.class);

        // Si la respuesta es null, lanzamos excepción
        if (response == null) {
            throw new ProductNotFoundException("Product with ID: " + id + " doesn't exist.");
        }

        // Mapear el producto obtenido a ProductoDTO
        return productMapper.productResponseToProductoDTO(response);
    }
}
