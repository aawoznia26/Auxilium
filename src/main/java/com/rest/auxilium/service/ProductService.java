package com.rest.auxilium.service;


import com.rest.auxilium.adapters.ProductAdapter;
import com.rest.auxilium.client.KauflandClient;
import com.rest.auxilium.client.KauflandPriceCalculator;
import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.Product;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.dto.kaufland.KauflandDto;
import com.rest.auxilium.dto.kaufland.KauflandOfferDto;
import com.rest.auxilium.repository.ProductRepository;
import com.rest.auxilium.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService implements KauflandPriceCalculator {

    @Autowired
    private KauflandClient kauflandClient;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private ProductAdapter productAdapter;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesService.class);

    public List<Product> fetchProducts(){
        LOGGER.info("Fetching Kaufland products");
        List<KauflandDto> kauflandDtoList = kauflandClient.getKauflandProducts();
        return productAdapter.createProducts(kauflandDtoList);
    }

    public Product collectProduct(Product product, String uuid){
        LOGGER.info("Collecting product started.");
        User user = userRepository.findFirstByUuid(uuid);
        int productPriceInPoints = product.getPrice();
        Product savedProduct = new Product();
        if(pointsService.getAllUserPoints(uuid) >= productPriceInPoints){
            LOGGER.info("User uuid: " + uuid + "has enough points to collect product id:"
                    + product.getId() + "worth " + product.getPrice() + " points");
            Points points = new Points(-productPriceInPoints, LocalDate.now(), PointStatus.USED, user);
            pointsService.savePoints(points);
            Product productToSave = new Product(product.getOfferId(), product.getTitle(), product.getSubtitle()
            ,product.getPrice(), product.getListImage());
            productToSave.setUser(user);
            savedProduct = productRepository.save(productToSave);
            LOGGER.info("Collecting product finished");
        }
        return savedProduct;

    }

    @Override
    public int calculatePrice(KauflandOfferDto kauflandOfferDto) {
        LOGGER.info("Calculating product price");
        double calculatedPrice = kauflandOfferDto.getPrice() * 1.73;
        if (kauflandOfferDto.getLabel().equals("specialItems")) {
            calculatedPrice =+ 64;
        } else {
            calculatedPrice = calculatedPrice * 0.97;
        }

        return (int) Math.round(calculatedPrice);
    }
}
