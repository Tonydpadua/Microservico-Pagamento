package com.tonydpadua.payment.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tonydpadua.payment.product.Product;
import com.tonydpadua.payment.sale.dto.SaleDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "stock",})
public class ProductDTO extends RepresentationModel<SaleDTO> implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("stock")
    private Integer stock;

    public static ProductDTO from(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }
}
