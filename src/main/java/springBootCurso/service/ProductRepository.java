package springBootCurso.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springBootCurso.entities.Productos;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Productos, Long > {
    List<Productos> findByCategoryNameIgnoreCase(String categoryName);
    @Query(value = "SELECT * FROM productos WHERE cost > :cost", nativeQuery = true)
    List<Productos> findProductosCaros(@Param("cost") Double cost);
    @Query("SELECT p FROM Productos p WHERE p.cost > :cost")
    List<Productos> findProductosCarosJQL(@Param("cost") Double cost);
}
