package com.rest.auxilium.domain;

import com.rest.auxilium.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 70)
    private String offerId;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String subtitle;

    private int price;

    private String listImage;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Product(String offerId, String title, String subtitle, int price, String listImage) {
        this.offerId = offerId;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.listImage = listImage;
    }

    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = new ProductDto(product.getOfferId(), product.getTitle(), product.getSubtitle()
                , Math.round(product.getPrice()), product.getListImage());
        return productDto;
    }

    public static List<ProductDto> mapToProductDtoList(List<Product> productList){
        return productList.stream().map(e -> mapToProductDto(e)).collect(Collectors.toList());
    }
}
