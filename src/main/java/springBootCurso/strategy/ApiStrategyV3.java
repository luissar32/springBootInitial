package springBootCurso.strategy;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.entities.Productos;
import springBootCurso.service.ProductMapperV2;
import springBootCurso.service.ProductRepository;
import springBootCurso.service.ProductoFileService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service("file")
public class ApiStrategyV3 implements ApiStrategy{
    private final ProductRepository productRepository;
    private final ProductMapperV2 productMapperV2;
    private final ProductoFileService productoFileService;
    private static final String FILE_PATH = "src/main/resources/productos.json";

    @Autowired
    public ApiStrategyV3(ProductRepository productRepository,
                         ProductMapperV2 productMapperV2,
                         ProductoFileService productoFileService) {
        this.productRepository = productRepository;
        this.productMapperV2 = productMapperV2;
        this.productoFileService = productoFileService;

    }

    @Override
    public List<ProductoDTO> obtenerProductos() {
        return List.of();  // Retorna una lista vacía o puedes agregar un manejo de excepción
    }

    // Implementación vacía para cumplir con la interfaz
    @Override
    public List<ProductoDTO> obtenerProductosPorCategoria(String categoryName) {
        return List.of();  // Retorna una lista vacía o puedes agregar un manejo de excepción
    }

    public List<ProductoDTO> leerProductosDesdeArchivo(String rutaArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Leer el archivo JSON y mapearlo a una lista de ProductoDTO
            return productoFileService.leerProductosDesdeArchivo(rutaArchivo);
        } catch (IOException e) {
            // Manejo de errores, por ejemplo, si el archivo no se puede leer
            e.printStackTrace();
            return List.of(); // Retornar una lista vacía en caso de error
        }
    }

    @Override
    public List<ProductoDTO> findProductosCaros(Double precio) {
        return List.of();
    }


}
