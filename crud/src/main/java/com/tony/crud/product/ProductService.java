package com.tony.crud.product;

import com.tony.crud.exceptions.ObjectNotFoundException;
import com.tony.crud.product.dto.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductVO create(ProductVO productVO) {
        return ProductVO.create(this.productRepository.save(Product.create(productVO)));
    }

    public Page<ProductVO> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable).map(this::convertToProductVO);
    }

    public ProductVO findById(Long id) {
        return ProductVO.create(this.productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product not found. Id: " + id)));
    }

    public ProductVO update(ProductVO productVO) {
        final Optional<Product> optionalProduct = this.productRepository.findById(productVO.getId());
        if (!optionalProduct.isPresent()) {
            throw new ObjectNotFoundException("Product not found. Id: " + productVO.getId());
        }
        return ProductVO.create(this.productRepository.save(Product.create(productVO)));
    }

    public void deleteById(Long id) {
        this.findById(id);
        this.productRepository.deleteById(id);
    }

    private ProductVO convertToProductVO(Product product) {
        return ProductVO.create(product);
    }
}
