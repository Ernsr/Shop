package er.shop.ernst.erinshop.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sweetshirts")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Sweetshirts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "sweetshirtcardimg")
    private String sweetshirtcardimg;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Categories> categories;

}
