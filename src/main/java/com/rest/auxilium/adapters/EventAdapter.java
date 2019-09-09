package com.rest.auxilium.adapters;

import com.rest.auxilium.domain.Event;
import com.rest.auxilium.dto.ticketMaster.TicketMasterDto;
import com.rest.auxilium.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventAdapter {

    @Autowired
    private EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventAdapter.class);

    private LocalDate date = null;

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public List<Event> createEvents(TicketMasterDto ticketMasterDto){
        if(ticketMasterDto.getEmbedded() != null){

            return ticketMasterDto.getEmbedded().getEvents().stream()
                    .map(e -> {
                        setDate(LocalDate.parse(e.getDates().getStart().getLocalDate()));
                        return new Event(e.getId(), e.getName(), e.getUrl(), e.getImages().get(3).getUrl(), date
                                , e.getClassifications().get(0).getSegment().getName(),  e.getClassifications().get(0).getGenre().getName()
                                , eventService.calculatePrice(e.getClassifications().get(0), e.getDates()));
                    }).collect(Collectors.toList());
        } else {
            LOGGER.error("Empty ticketMaster response");
            return new ArrayList<>();
        }

    }



}
