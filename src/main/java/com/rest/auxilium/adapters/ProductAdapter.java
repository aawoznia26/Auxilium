package com.rest.auxilium.adapters;


import com.rest.auxilium.domain.Product;
import com.rest.auxilium.dto.kaufland.KauflandDto;
import com.rest.auxilium.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAdapter{

    @Autowired
    private ProductService productService;


    public List<Product> createProducts(List<KauflandDto> kauflandDtoList){
        return kauflandDtoList.stream().flatMap(e -> e.getCategories().stream().flatMap(c -> c.getOffers().stream()))
                .map(o -> {
                    return new Product(o.getOfferId(), o.getTitle(), o.getSubtitle(), productService.calculatePrice(o)
                            , o.getListImage());
                }).collect(Collectors.toList());
    }
}
