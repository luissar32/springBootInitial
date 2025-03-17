package springBootCurso.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.entities.Productos;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapperV2 {

    default ProductoDTO productosToProductoDTO(Productos productos) {
        if (productos == null) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setProductId(productos.getProductId());
        productoDTO.setName(productos.getName());
        productoDTO.setCost(productos.getCost());
        productoDTO.setDetails(productos.getDetails());
        productoDTO.setCategoryName(productos.getCategoryName());
        productoDTO.setImageUrl(productos.getImageUrl());

        return productoDTO;
    }

    List<ProductoDTO> productosToProductoDTO(List<Productos> productos);
}
