package springBootCurso.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import springBootCurso.domain.ProductResponse;
import springBootCurso.dto.ProductoDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "id", target = "productId")
    @Mapping(source = "title", target = "name")
    @Mapping(source = "price", target = "cost")
    @Mapping(source = "description", target = "details")
    @Mapping(source = "category", target = "categoryName")
    @Mapping(source = "image", target = "imageUrl")
    ProductoDTO productResponseToProductoDTO(ProductResponse productResponse);


}
