package com.tony.crud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tony.crud.product.Product;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({"id","name","stock","price",})
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("price")
    private Double price;

    public static ProductVO create(Product product) {
        return new ModelMapper().map(product, ProductVO.class);
    }
}
