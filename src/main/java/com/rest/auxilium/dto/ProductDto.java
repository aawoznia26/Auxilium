package com.rest.auxilium.dto;

import com.rest.auxilium.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String offerId;
    private String title;
    private String subtitle;
    private int price;
    private String listImage;

    public ProductDto(String offerId, String title, String subtitle, int price, String listImage) {
        this.offerId = offerId;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.listImage = listImage;
    }

    public static Product mapToProduct(ProductDto productDto){
        Product product = new Product(productDto.getOfferId(), productDto.getTitle(), productDto.getSubtitle(), productDto.getPrice()
                , productDto.getListImage());
        return product;
    }

    public static List<Product> mapToProductList(List<ProductDto> productDtoList){
        return productDtoList.stream().map(e -> mapToProduct(e)).collect(Collectors.toList());
    }
}
