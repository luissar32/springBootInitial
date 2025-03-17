package springBootCurso.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.entities.Productos;
import springBootCurso.service.ProductMapperV2;
import springBootCurso.service.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service("db")
public class ApiStrategyV2 implements ApiStrategy {

    private final ProductRepository productRepository;
    private final ProductMapperV2 productMapperV2;

    @Autowired
    public ApiStrategyV2(ProductRepository productRepository, ProductMapperV2 productMapperV2) {
        this.productRepository = productRepository;
        this.productMapperV2 = productMapperV2;
    }

    @Override
    public List<ProductoDTO> obtenerProductos() {
        List<Productos> productos = productRepository.findAll();
        return productos.stream()
                .map(productMapperV2::productosToProductoDTO)  // Usamos el mapeo definido en ProductMapperV2
                .collect(Collectors.toList());
    }
    public List<ProductoDTO> obtenerProductosPorCategoria(String categoryName){
    List<Productos> productos = productRepository.
            findByCategoryNameIgnoreCase(categoryName);
    return productos.stream().map(productMapperV2::productosToProductoDTO).
            collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> leerProductosDesdeArchivo(String rutaArchivo) {
        return List.of();
    }
    @Override
    public List<ProductoDTO> findProductosCaros(Double precio) {
        // Busca los productos que tienen un precio mayor o igual al valor recibido
        List<Productos> productos = productRepository.findProductosCaros(precio);
        return productos.stream()
                .map(productMapperV2::productosToProductoDTO)
                .collect(Collectors.toList());
    }
    public List<ProductoDTO> findProductosCarosJPQL(double precio) {
        List<Productos> productos = productRepository.findProductosCarosJQL(precio);
        return productos.stream()
                .map(productMapperV2::productosToProductoDTO)
                .collect(Collectors.toList());
    }

}
