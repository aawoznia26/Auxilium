package com.rest.auxilium.service;


import com.rest.auxilium.adapters.EventAdapter;
import com.rest.auxilium.client.TicketMasterClient;
import com.rest.auxilium.client.TicketMasterPriceCalculator;
import com.rest.auxilium.domain.Event;
import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.dto.ticketMaster.TicketMasterClassificationDto;
import com.rest.auxilium.dto.ticketMaster.TicketMasterDatesDto;
import com.rest.auxilium.dto.ticketMaster.TicketMasterDto;
import com.rest.auxilium.repository.EventRepository;
import com.rest.auxilium.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class EventService implements TicketMasterPriceCalculator {

    private static final int basicPrice = 368;

    @Autowired
    private TicketMasterClient ticketMasterClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventAdapter eventAdapter;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public List<Event> fetchEvents(){
        LOGGER.info("Fetching events");
        TicketMasterDto ticketMasterDto = ticketMasterClient.getTicketMasterEvents();
        return eventAdapter.createEvents(ticketMasterDto);
    }


    public Event collectEvent(Event event, String uuid){
        LOGGER.info("Collecting event started");
        User user = userRepository.findFirstByUuid(uuid);
        int eventPriceInPoints = event.getPriceInPoints();
        Event savedEvent = new Event();
        if(pointsService.getAllUserPoints(uuid) >= eventPriceInPoints){
            LOGGER.info("User uuid: " + uuid + "has enough points to collect event id:"
                    + event.getId() + "worth " + event.getPriceInPoints() + " points");
            Points points = new Points(-eventPriceInPoints, LocalDate.now(), PointStatus.USED, user);
            pointsService.savePoints(points);
            Event eventToSave = new Event(event.getId(), event.getName(), event.getUrl() ,event.getImage()
                    , event.getDate(), event.getSegment(), event.getSubsegment(), event.getPriceInPoints());
            eventToSave.setUser(user);
            savedEvent = eventRepository.save(eventToSave);
            LOGGER.info("Collecting product finished");
        }
        return savedEvent;

    }

    @Override
    public int calculatePrice(TicketMasterClassificationDto classificationDto, TicketMasterDatesDto ticketMasterDatesDto) {
        LOGGER.info("Calculating event price");
        double price = 0;

        if (classificationDto.getSegment().getName().equals("Music")) {
            price = basicPrice * 1.46;
        } else {
            price = basicPrice * 1.18;
        }


        if (classificationDto.getGenre().getName().equals("Rock") || classificationDto.getGenre().getName().equals("Alternative")) {
            price = basicPrice * 1.36;
        } else {
            price = basicPrice - 76;
        }

        if (LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.JUNE
                || LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.JULY
                || LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.AUGUST) {
            price = basicPrice + 56;
        } else if (LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.NOVEMBER
                || LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.FEBRUARY
                || LocalDate.parse(ticketMasterDatesDto.getStart().getLocalDate()).getMonth() == Month.OCTOBER) {
            price = basicPrice * 0.79;
        }

        return (int) Math.round(price);
    }

}
