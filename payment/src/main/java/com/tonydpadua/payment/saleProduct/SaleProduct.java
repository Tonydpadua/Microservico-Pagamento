package com.tonydpadua.payment.saleProduct;

import com.tonydpadua.payment.sale.Sale;
import com.tonydpadua.payment.saleProduct.dto.SaleProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sale_product")
public class SaleProduct implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_product", nullable = false, length = 10)
    private Long idProduct;

    @Column(name = "quantity", nullable = false, length = 10)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale")
    private Sale sale;

    public static SaleProduct from(SaleProductDTO saleProductDTO) {
        return new ModelMapper().map(saleProductDTO, SaleProduct.class);
    }
}
