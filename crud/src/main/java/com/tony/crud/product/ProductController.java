package com.tony.crud.product;

import com.tony.crud.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;
    private final PagedResourcesAssembler<ProductDTO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml",})
    public ProductDTO findById(@PathVariable Long id) {
        ProductDTO productDTO = this.productService.findById(id);
        productDTO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return productDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml",})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

        Page<ProductDTO> products = this.productService.findAll(pageable);
        products.stream().forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProductDTO>> pagedModel = this.assembler.toModel(products);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }
}
