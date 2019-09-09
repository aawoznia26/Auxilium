package com.rest.auxilium.controllers;


import com.google.gson.Gson;
import com.rest.auxilium.domain.Product;
import com.rest.auxilium.dto.ProductDto;
import com.rest.auxilium.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getAvailableProductsTest() throws Exception{

        //Given
        Product product1 = new Product("id1", "test product1", "test1",  176, "test url1");
        Product product2 = new Product("id2", "test product2", "test2", 276, "test url2");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        when(productService.fetchProducts()).thenReturn(productList);

        //When & Then
        mockMvc.perform(get("/v1/products").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is("test product1")));
    }

    @Test
    public void collectProductTest() throws Exception{

        //Given
        Product product1 = new Product("id1", "test product1", "test1",  176, "test url1");
        ProductDto productDto1 = new ProductDto("id1", "test product1", "test1",  176, "test url1");
        String uuid = "jgekbckrjebckrhje";
        when(productService.collectProduct(product1, uuid)).thenReturn(product1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto1);

        //When & Then
        mockMvc.perform(post("/v1/products/" + uuid).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));

    }


}
