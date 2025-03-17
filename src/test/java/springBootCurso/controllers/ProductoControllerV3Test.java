package springBootCurso.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springBootCurso.dto.ProductoDTO;
import springBootCurso.strategy.ApiService;
import springBootCurso.strategy.ApiStrategyV2;
import springBootCurso.strategy.ApiStrategyV3;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductoControllerV3.class)
public class ProductoControllerV3Test {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ApiService apiService;
    @Mock
    private ApiStrategyV2 dbStrategy;
    @Mock
    private ApiStrategyV3 fileStrategy;
    @InjectMocks
    private ProductoControllerV3 productoControllerV3;

    @Test
    void testGetProductsV3_FakeSource() throws Exception {
        // Crear instancias de ProductoDTO con los datos correctos
        ProductoDTO producto1 = new ProductoDTO();
        producto1.setProductId(1L);
        producto1.setName("Producto A");
        producto1.setCost(100.0);
        producto1.setDetails("Descripción del Producto A");
        producto1.setCategoryName("Electrónica");
        producto1.setImageUrl("https://example.com/productoA.jpg");

        ProductoDTO producto2 = new ProductoDTO();
        producto2.setProductId(2L);
        producto2.setName("Producto B");
        producto2.setCost(150.0);
        producto2.setDetails("Descripción del Producto B");
        producto2.setCategoryName("Ropa");
        producto2.setImageUrl("https://example.com/productoB.jpg");

        List<ProductoDTO> productos = Arrays.asList(producto1, producto2);

        // Simular respuesta del servicio
        when(apiService.obtenerProductosPorFuente(Mockito.eq("FAKE"), Mockito.any()))
                .thenReturn(productos);

        // Ejecutar petición y verificar respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/sistema/api/v3/products")
                        .param("source", "FAKE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Producto A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].details").value("Descripción del Producto A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("Electrónica"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl").value("https://example.com/productoA.jpg"));
    }
    @Test
    void testGetProductsV3_ContextNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sistema/api/v3/products")
                        .param("source", "INVALID"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    void testGetProductosCaros_UsandoSQL() throws Exception {
        // Crear un ProductoDTO con la estructura correcta
        ProductoDTO producto1 = new ProductoDTO();
        producto1.setProductId(3L);
        producto1.setName("Producto C");
        producto1.setCost(500.0);
        producto1.setDetails("Descripción del Producto C");
        producto1.setCategoryName("Lujo");
        producto1.setImageUrl("https://example.com/productoC.jpg");

        List<ProductoDTO> productos = List.of(producto1);

        // Simular respuesta de la base de datos
        when(dbStrategy.findProductosCaros(300.0)).thenReturn(productos);

        // Ejecutar la petición y verificar la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/sistema/api/v3/caros")
                        .param("precio", "300")
                        .param("uSQL", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Producto C"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(500.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].details").value("Descripción del Producto C"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("Lujo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl").value("https://example.com/productoC.jpg"));
    }

}
