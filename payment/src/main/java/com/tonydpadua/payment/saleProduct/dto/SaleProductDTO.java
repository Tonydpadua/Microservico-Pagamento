package com.tonydpadua.payment.saleProduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tonydpadua.payment.sale.Sale;
import com.tonydpadua.payment.sale.dto.SaleDTO;
import com.tonydpadua.payment.saleProduct.SaleProduct;
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
@JsonPropertyOrder({"id", "idProduct", "quantity", "sale"})
public class SaleProductDTO extends RepresentationModel<SaleDTO> implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idProduct")
    private Long idProduct;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("sale")
    private SaleDTO saleDTO;

    public static SaleProductDTO from(SaleProduct saleProduct) {
        return new ModelMapper().map(saleProduct, SaleProductDTO.class);
    }
}
