package com.rest.auxilium.client;


import com.rest.auxilium.config.TicketMasterConfig;
import com.rest.auxilium.dto.ticketMaster.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketMasterClientTestSuite {

    @InjectMocks
    private TicketMasterClient ticketMasterClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TicketMasterConfig ticketMasterConfig;

    @Before
    public void init() {
        when(ticketMasterConfig.getEventEndpoint()).thenReturn("https://app.ticketmaster.com/discovery/v2/events.json");
        when(ticketMasterConfig.getCountryCode()).thenReturn("countryCode=PL");
        when(ticketMasterConfig.getApiKey()).thenReturn("apikey=t5EIJfzmAdFHDYXYtvGp3AArvbJqATXS");
    }

    @Test
    public void shouldFetchObject() throws URISyntaxException {

        // Given
        TicketMasterEventDto ticketMasterEventDto = new TicketMasterEventDto("test", "shsyTesthbhj", "testurl", new ArrayList<>(), new TicketMasterDatesDto(), new ArrayList<>());
        List<TicketMasterEventDto> ticketMasterEventDtoList = new ArrayList<>();
        ticketMasterEventDtoList.add(ticketMasterEventDto);
        TicketMasterDto ticketMasterDto = new TicketMasterDto(new TicketMasterEmbeddedDto(ticketMasterEventDtoList));
        URI url = new URI("https://app.ticketmaster.com/discovery/v2/events.json?countryCode=PL&apikey=t5EIJfzmAdFHDYXYtvGp3AArvbJqATXS");

        when(restTemplate.getForObject(url, TicketMasterDto.class)).thenReturn(ticketMasterDto);

        // When
        TicketMasterDto fetchedTicketMasterDto = ticketMasterClient.getTicketMasterEvents();

        // Then
        assertNotNull(fetchedTicketMasterDto);
        assertEquals(ticketMasterEventDto, fetchedTicketMasterDto.getEmbedded().getEvents().get(0));
        assertEquals("test", fetchedTicketMasterDto.getEmbedded().getEvents().get(0).getName());

    }

}
