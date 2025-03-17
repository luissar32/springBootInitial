package springBootCurso.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name="productos")
@Getter
@Setter
@NoArgsConstructor
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column
    private String name;
    @Column
    private Double cost;
    @Column
    private String details;
    @Column
    private String categoryName;
    @Column
    private String imageUrl;

}
