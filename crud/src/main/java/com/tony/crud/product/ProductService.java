package com.tony.crud.product;

import com.tony.crud.product.dto.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void create(ProductVO productVO) {

    }
}
