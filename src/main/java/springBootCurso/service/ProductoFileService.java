package springBootCurso.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import springBootCurso.dto.ProductoDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductoFileService {

    public List<ProductoDTO> leerProductosDesdeArchivo(String rutaArchivo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Leer el archivo y mapearlo a una lista de ProductoDTO
        return objectMapper.readValue(new File(rutaArchivo), new TypeReference<List<ProductoDTO>>() {});
    }
}
