package com.rest.auxilium.controllers;

import com.rest.auxilium.domain.Event;
import com.rest.auxilium.dto.EventDto;
import com.rest.auxilium.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public List<EventDto> getAvailableEvent() { return Event.mapToEventDtoList(eventService.fetchEvents()); }

    @RequestMapping(method = RequestMethod.POST, value = "/events/{uuid}")
    public void collectEvent(@RequestBody EventDto eventDto, @PathVariable String uuid) {
        eventService.collectEvent(EventDto.mapToEvent(eventDto), uuid); }

}
