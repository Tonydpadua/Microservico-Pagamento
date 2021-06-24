package com.tonydpadua.payment.sale;

import com.tonydpadua.payment.exceptions.ObjectNotFoundException;
import com.tonydpadua.payment.sale.dto.SaleDTO;
import com.tonydpadua.payment.saleProduct.SaleProduct;
import com.tonydpadua.payment.saleProduct.SaleProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {


    private final SaleRepository saleRepository;
    private final SaleProductRepository saleProductRepository;

    public SaleDTO save(SaleDTO saleDTO) {
        Sale sale = this.saleRepository.save(Sale.from(saleDTO));

        List<SaleProduct> saleProductsSaved = new ArrayList<>();
        saleDTO.getSaleProductDTOS().forEach(saleProductDTO -> {
            SaleProduct saleProduct = SaleProduct.from(saleProductDTO);
            saleProduct.setSale(sale);
            saleProductsSaved.add(this.saleProductRepository.save(saleProduct));
        });
        sale.setProducts(saleProductsSaved);

        return SaleDTO.from(sale);
    }

    public Page<SaleDTO> findAll(Pageable pageable) {
        return this.saleRepository.findAll(pageable).map(this::convertToProductVO);
    }

    public SaleDTO findById(Long id) {
        return SaleDTO.from(this.saleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Sale not found. Id: " + id)));
    }

    private SaleDTO convertToProductVO(Sale sale) {
        return SaleDTO.from(sale);
    }
}
