package springBootCurso.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springBootCurso.domain.ProductResponse;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.service.ProductMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fake")
public class ApiStrategyV1 implements ApiStrategy {

    private static final String URL_V1 = "https://fakestoreapi.com/products";
    private final RestTemplate restTemplate;
    private final ProductMapper productMapper;


    @Autowired
    public ApiStrategyV1(RestTemplate restTemplate, ProductMapper productMapper){
        this.restTemplate = restTemplate;
        this.productMapper = productMapper;

    }

    @Override
    public List<ProductoDTO> obtenerProductos(){
        ProductResponse[] products = restTemplate.getForObject(URL_V1, ProductResponse[].class);
        if (products != null) {
            return Arrays.stream(products)
                    .map(productMapper::productResponseToProductoDTO)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }

    }
    @Override
    public List<ProductoDTO> obtenerProductosPorCategoria(String category){
        return List.of();
    }

    @Override
    public List<ProductoDTO> leerProductosDesdeArchivo(String rutaArchivo) {
        return List.of();
    }

    @Override
    public List<ProductoDTO> findProductosCaros(Double precio) {
        return List.of();
    }


}
