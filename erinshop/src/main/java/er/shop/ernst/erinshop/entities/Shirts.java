package er.shop.ernst.erinshop.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shirts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shirts {

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

    @Column(name = "shirtcardimg")
    private String shirtcardimg;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Categories> categories;

}
