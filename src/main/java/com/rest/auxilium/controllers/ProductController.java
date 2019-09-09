package com.rest.auxilium.controllers;


import com.rest.auxilium.domain.Product;
import com.rest.auxilium.dto.ProductDto;
import com.rest.auxilium.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public List<ProductDto> getAvailableProducts() {
        return  Product.mapToProductDtoList(productService.fetchProducts()); }


    @RequestMapping(method = RequestMethod.POST, value = "/products/{uuid}")
    public void collectProduct(@RequestBody ProductDto productDto, @PathVariable String uuid) {
        productService.collectProduct(ProductDto.mapToProduct(productDto), uuid); }
}