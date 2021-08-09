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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ProductDTO findById(@PathVariable("id") Long id) {
        return this.productService.findById(id);
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

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml",},
            consumes = {"application/json", "application/xml", "application/x-yaml",})
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        return this.productService.save(productDTO);
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml",},
            consumes = {"application/json", "application/xml", "application/x-yaml",})
    public ProductDTO update(@RequestBody ProductDTO productDTO) {
        return this.productService.update(productDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
