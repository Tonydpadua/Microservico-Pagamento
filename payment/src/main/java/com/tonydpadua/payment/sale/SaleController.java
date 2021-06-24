package com.tonydpadua.payment.sale;

import com.tonydpadua.payment.sale.dto.SaleDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sale")
public class SaleController {


    private final SaleService saleService;
    private final PagedResourcesAssembler<SaleDTO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml",})
    public SaleDTO findById(@PathVariable("id") Long id) {
        SaleDTO saleDTO = this.saleService.findById(id);
        saleDTO.add(linkTo(methodOn(SaleController.class).findById(id)).withSelfRel());
        return saleDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml",})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "saleDate"));

        Page<SaleDTO> saleDTOS = this.saleService.findAll(pageable);
        saleDTOS.stream().forEach(p -> p.add(linkTo(methodOn(SaleController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<SaleDTO>> pagedModel = this.assembler.toModel(saleDTOS);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml",},
            consumes = {"application/json", "application/xml", "application/x-yaml",})
    public SaleDTO save(@RequestBody SaleDTO saleDTO) {
        SaleDTO saleDTOCreated = this.saleService.save(saleDTO);
        saleDTOCreated.add(linkTo(methodOn(SaleController.class).findById(saleDTOCreated.getId())).withSelfRel());
        return saleDTOCreated;
    }
}
