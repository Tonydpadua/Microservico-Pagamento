package com.tony.crud.product;

import com.tony.crud.product.dto.ProductVO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    @Column(name = "price", nullable = false, length = 10)
    private Double price;

    public static Product create(ProductVO productVO) {
        return new ModelMapper().map(productVO, Product.class);
    }

}
