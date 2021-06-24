package com.tonydpadua.payment.sale;

import com.tonydpadua.payment.sale.dto.SaleDTO;
import com.tonydpadua.payment.saleProduct.SaleProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sale")
public class Sale implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "DD/mm/yyyy")
    @Column(name = "saleDate", nullable = false, length = 10)
    private Date saleDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sale", cascade = {CascadeType.REFRESH})
    private List<SaleProduct> saleProducts;

    @Column(name = "totalValue", nullable = false, length = 10)
    private Integer totalValue;

    public static Sale from(SaleDTO saleDTO) {
        return new ModelMapper().map(saleDTO, Sale.class);
    }
}
