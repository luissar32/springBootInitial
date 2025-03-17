package springBootCurso.strategy;

import springBootCurso.dto.ProductoDTO;

import java.util.List;

public interface ApiStrategy {
    List<ProductoDTO> obtenerProductos();
    List<ProductoDTO> obtenerProductosPorCategoria(String category);
    List<ProductoDTO> leerProductosDesdeArchivo(String rutaArchivo);
    List<ProductoDTO> findProductosCaros(Double precio);

}
