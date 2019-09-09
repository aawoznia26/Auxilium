package com.rest.auxilium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.auxilium.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long internalId;
    private String id;
    private String name;
    private String url;
    private String image;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String segment;
    private String subsegment;
    private int priceInPoints;

    public EventDto(String id, String name, String url, String image, LocalDate date, String segment, String subsegment, int priceInPoints) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.image = image;
        this.date = date;
        this.segment = segment;
        this.subsegment = subsegment;
        this.priceInPoints = priceInPoints;
    }


    public static Event mapToEvent(EventDto eventDto){
        Event event = new Event(eventDto.getId(), eventDto.getName(), eventDto.getUrl(), eventDto.getImage()
        , eventDto.getDate(), eventDto.getSegment(), eventDto.getSubsegment(), eventDto.getPriceInPoints());
        return event;
    }

    public static List<Event> mapToEventList(List<EventDto> eventDtoList){
        return eventDtoList.stream().map(e -> mapToEvent(e)).collect(Collectors.toList());
    }
}
