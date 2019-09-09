package com.rest.auxilium.client;

import com.rest.auxilium.config.KauflandConfig;
import com.rest.auxilium.dto.kaufland.KauflandCategoryDto;
import com.rest.auxilium.dto.kaufland.KauflandDto;
import com.rest.auxilium.dto.kaufland.KauflandOfferDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KauflandClientTestSuite {

    @InjectMocks
    private KauflandClient kauflandClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private KauflandConfig kauflandConfig;

    @Before
    public void init() {
        when(kauflandConfig.getOfferEndpoint()).thenReturn("https://api.kaufland.net/offers/api/v5/");
        when(kauflandConfig.getStoreId()).thenReturn("PL7360");
        when(kauflandConfig.getSubscriptionKey()).thenReturn("7234843e4be34f2980b40fe92d6e6554");
    }

    @Test
    public void shouldFetchObject() throws URISyntaxException {


        // Given
        KauflandOfferDto kauflandOfferDto = new KauflandOfferDto("testID", "shsyTesthbhj", "testDSDSV", 765.98, "testLabel","testurl");
        List<KauflandOfferDto> productsOffersList = new ArrayList<>();
        productsOffersList.add(kauflandOfferDto);
        List<KauflandCategoryDto> categoryDtoList = new ArrayList<>();
        KauflandCategoryDto categoryDto = new KauflandCategoryDto("testCategory", productsOffersList);
        categoryDtoList.add(categoryDto);
        KauflandDto kauflandDto = new KauflandDto(categoryDtoList);
        KauflandDto[] kauflandDtoList = new KauflandDto[1];
        kauflandDtoList[0] = kauflandDto;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", kauflandConfig.getSubscriptionKey());
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        URI url = new URI("https://api.kaufland.net/offers/api/v5/PL7360");

        ResponseEntity<KauflandDto[]> respEntity = new ResponseEntity(kauflandDtoList, HttpStatus.OK);
        when(restTemplate.exchange(url, HttpMethod.GET, entity, KauflandDto[].class)).thenReturn(respEntity);

        // When
        List<KauflandDto> fetchedKauflandDtoList = kauflandClient.getKauflandProducts();

        // Then
        assertNotNull(fetchedKauflandDtoList);
        assertEquals(categoryDto, fetchedKauflandDtoList.get(0).getCategories().get(0));

    }

}
