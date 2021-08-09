package com.tony.crud.product;

import com.tony.crud.config.ProductSendMessage;
import com.tony.crud.exceptions.ObjectNotFoundException;
import com.tony.crud.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductSendMessage productSendMessage;

    public ProductDTO save(ProductDTO productDTO) {
        this.productSendMessage.sendMessage(ProductDTO.from(this.productRepository.save(Product.from(productDTO))));
        ProductDTO productDTOCreated = ProductDTO.from(this.productRepository.save(Product.from(productDTO)));
        productDTOCreated.add(linkTo(methodOn(ProductController.class).findById(productDTOCreated.getId())).withSelfRel());
        return productDTOCreated;
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable).map(this::convertToProductVO);
    }

    public ProductDTO findById(Long id) {
        ProductDTO productDTO = ProductDTO.from(this.productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product not found. Id: " + id)));
        productDTO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return productDTO;
    }

    public ProductDTO update(ProductDTO productDTO) {
        final Optional<Product> optionalProduct = this.productRepository.findById(productDTO.getId());
        if (!optionalProduct.isPresent()) {
            throw new ObjectNotFoundException("Product not found. Id: " + productDTO.getId());
        }
        ProductDTO productDTOUpdate = ProductDTO.from(this.productRepository.save(Product.from(productDTO)));
        productDTOUpdate.add(linkTo(methodOn(ProductController.class).findById(productDTO.getId())).withSelfRel());
        return productDTOUpdate;
    }

    public void deleteById(Long id) {
        this.findById(id);
        this.productRepository.deleteById(id);
    }

    private ProductDTO convertToProductVO(Product product) {
        return ProductDTO.from(product);
    }
}
