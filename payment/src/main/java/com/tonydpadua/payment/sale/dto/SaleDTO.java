package com.tonydpadua.payment.sale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tonydpadua.payment.sale.Sale;
import com.tonydpadua.payment.saleProduct.dto.SaleProductDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "saleDate", "saleProducts", "totalValue",})
public class SaleDTO extends RepresentationModel<SaleDTO> implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("saleDate")
    private Date saleDate;

    @JsonProperty("saleProducts")
    private List<SaleProductDTO> saleProducts;

    @JsonProperty("totalValue")
    private Integer totalValue;

    public static SaleDTO from(Sale sale) {
        return new ModelMapper().map(sale, SaleDTO.class);
    }
}
