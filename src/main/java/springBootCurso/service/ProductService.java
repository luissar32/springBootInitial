package springBootCurso.service;

import springBootCurso.domain.ProductResponse;

import java.util.List;

public interface ProductService {

    //metodo abstracto.
    public List<ProductResponse> getProducts();
}
