package springBootCurso.strategy;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import springBootCurso.domain.ProductResponse;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.service.ProductMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiStrategyV1Test {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ApiStrategyV1 apiStrategyV1;

    private ProductResponse productResponse;
    private ProductoDTO productoDTO;

    @Test
    void testObtenerProductos(){
        //simular respuesta de la API
        ProductResponse[] mockResponse = new ProductResponse[]{productResponse};
        when(restTemplate.getForObject("https://fakestoreapi.com/products", ProductResponse[].class))
                .thenReturn(mockResponse);
        //simula la conversión del mapper.
        when(productMapper.productResponseToProductoDTO(productResponse)).thenReturn(productoDTO);
        List<ProductoDTO> productos = apiStrategyV1.obtenerProductos();

        // Verificar resultado
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Producto Prueba", productos.get(0).getName());

        // Verificar que los mocks fueron llamados
        verify(restTemplate, times(1)).getForObject(anyString(), eq(ProductResponse[].class));
        verify(productMapper, times(1)).productResponseToProductoDTO(any());


    }
    @Test
    void testObtenerProductos_ApiRetornaNull() {
        when(restTemplate.getForObject(anyString(), eq(ProductResponse[].class)))
                .thenReturn(null);

        List<ProductoDTO> productos = apiStrategyV1.obtenerProductos();

        assertNotNull(productos);
        assertTrue(productos.isEmpty());
    }



}
