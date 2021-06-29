package com.tony.crud.product;

import com.tony.crud.config.ProductSendMessage;
import com.tony.crud.exceptions.ObjectNotFoundException;
import com.tony.crud.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductSendMessage productSendMessage;

    public ProductDTO save(ProductDTO productDTO) {
        this.productSendMessage.sendMessage(ProductDTO.from(this.productRepository.save(Product.from(productDTO))));
        return ProductDTO.from(this.productRepository.save(Product.from(productDTO)));
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable).map(this::convertToProductVO);
    }

    public ProductDTO findById(Long id) {
        return ProductDTO.from(this.productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product not found. Id: " + id)));
    }

    public ProductDTO update(ProductDTO productDTO) {
        final Optional<Product> optionalProduct = this.productRepository.findById(productDTO.getId());
        if (!optionalProduct.isPresent()) {
            throw new ObjectNotFoundException("Product not found. Id: " + productDTO.getId());
        }
        return ProductDTO.from(this.productRepository.save(Product.from(productDTO)));
    }

    public void deleteById(Long id) {
        this.findById(id);
        this.productRepository.deleteById(id);
    }

    private ProductDTO convertToProductVO(Product product) {
        return ProductDTO.from(product);
    }
}
