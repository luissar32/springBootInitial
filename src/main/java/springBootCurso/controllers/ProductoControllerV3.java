package springBootCurso.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.exceptions.ContextNotFoundException;
import springBootCurso.strategy.ApiService;
import springBootCurso.strategy.ApiStrategy;
import springBootCurso.strategy.ApiStrategyV2;
import springBootCurso.strategy.ApiStrategyV3;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sistema/api/v3")
public class ProductoControllerV3 {

    private final ApiService apiService;
    private final ApiStrategyV2 dbStrategy;
    private final ApiStrategyV3 fileStrategy;

    public ProductoControllerV3(ApiService apiService, @Qualifier("db") ApiStrategyV2 dbStrategy,
                                ApiStrategyV3 fileStrategy) {
        this.apiService = apiService;
        this.dbStrategy = dbStrategy;
        this.fileStrategy = fileStrategy;
    }

    @GetMapping("/products")
    public List<ProductoDTO> getProductsV3(
            @RequestParam(name = "source", defaultValue = "FAKE") String source,
            @RequestParam(name = "category", required = false) String categoryName,
            @RequestParam(name = "precio", required = false, defaultValue = "0.0") double precio
    ) throws ContextNotFoundException {
        List<ProductoDTO> productos;

        // Filtrado por categoría
        if ("madeinargentina".equalsIgnoreCase(categoryName)) {
            productos = fileStrategy.leerProductosDesdeArchivo("src/main/resources/productos.json");
        } else if ("ropa".equalsIgnoreCase(categoryName)) {
            productos = dbStrategy.obtenerProductosPorCategoria("ropa");
        } else {
            // Convertimos el valor de "source" a mayúsculas para que la comparación no sea sensible a mayúsculas/minúsculas
            String normalizedSource = source.toUpperCase();

            // Validación de la fuente para lanzar la excepción si es inválida
            if (normalizedSource == null || (!normalizedSource.equals("FAKE") && !normalizedSource.equals("DB"))) {
                throw new ContextNotFoundException("La fuente de datos '" + source + "' no es válida.");
            }

            productos = apiService.obtenerProductosPorFuente(normalizedSource, categoryName != null ? categoryName : null);
        }

        // Filtrado por precio usando 'cost' en lugar de 'precio'
        return productos.stream()
                .filter(product -> product.getCost() >= precio)  // Filtra productos por 'cost'
                .collect(Collectors.toList());
    }

    @GetMapping("/caros")
    public List<ProductoDTO> getProductosCaros(@RequestParam(name = "precio", required = false, defaultValue = "0") Double precio,
                                               @RequestParam(name = "uSQL", required = false, defaultValue = "false") boolean uSQL
    ) {
        if (uSQL) {
            System.out.println("🟠 Usando SQL Nativo para buscar productos caros con precio > " + precio);
            return dbStrategy.findProductosCaros(precio);
        } else {
            System.out.println("🔵 Usando JPQL para buscar productos caros con precio > " + precio);
            return dbStrategy.findProductosCarosJPQL(precio);
        }

    }
}
