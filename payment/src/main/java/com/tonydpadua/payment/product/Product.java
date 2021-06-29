package com.tonydpadua.payment.product;

import com.tonydpadua.payment.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    public static Product from(ProductDTO productDTO) {
        return new ModelMapper().map(productDTO, Product.class);
    }
}
