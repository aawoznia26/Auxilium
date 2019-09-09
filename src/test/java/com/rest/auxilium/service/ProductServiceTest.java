package com.rest.auxilium.service;

import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.Product;
import com.rest.auxilium.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointsService pointsService;

    @Test
    public void shouldFetchProducts() {

        //Given

        //When
        List<Product> productList = productService.fetchProducts();

        //Then
        Assert.assertNotEquals(0, productList.size());

    }

    @Test
    public void collectProductTest() {

        //Given
        User user = new User("Włodek", 872736562, "dcvdfvfvd@gmailo.com", "Fnasnus#64723");
        User savedUser = userService.saveUser(user);

        Points points = new Points(276, savedUser);
        pointsService.issuePointsToUser(points);

        Product product = new Product("hsbhbjjce", "test product", "test product", 276, "testimage.jpg");
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        savedUser.setProduct(productList);

        //When
        Product collectedProduct = productService.collectProduct(product, savedUser.getUuid());

        //Then
        Assert.assertNotEquals(null, collectedProduct.getUser());
        Assert.assertNotEquals(0, savedUser.getProduct().size());


    }
}
