package springBootCurso.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springBootCurso.dto.ProductoDTO;

import java.util.List;

@Service
public class ApiService {
    private final ApiStrategy fakeStrategy;
    private final ApiStrategy dbStrategy;

    @Autowired
    public ApiService(@Qualifier("fake") ApiStrategy fake,
                      @Qualifier("db") ApiStrategy dbStrategy) {
        this.fakeStrategy = fake;
        this.dbStrategy = dbStrategy;
    }

    public List<ProductoDTO> obtenerProductosPorFuente(String source, String category) {
        if ("ROPA".equalsIgnoreCase(category)){
            return dbStrategy.obtenerProductosPorCategoria(category);
        }
        if ("DB".equalsIgnoreCase(source)) {
            return dbStrategy.obtenerProductos();
        } else {
            return fakeStrategy.obtenerProductos(); // FAKE es el default
        }
    }
}
