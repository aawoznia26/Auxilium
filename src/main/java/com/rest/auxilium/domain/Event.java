package com.rest.auxilium.domain;

import com.rest.auxilium.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event{

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long internalId;

    @Column(length = 70)
    private String id;

    @Column(length = 100)
    private String name;

    private String url;

    private String image;

    private LocalDate date;

    private String segment;

    private String subsegment;

    private int priceInPoints;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Event(String id, String name, String url, String image, LocalDate date, String segment, String subsegment, int priceInPoints) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.image = image;
        this.date = date;
        this.segment = segment;
        this.subsegment = subsegment;
        this.priceInPoints = priceInPoints;
    }

    public static EventDto mapToEventDto(Event event){
        EventDto eventDto = new EventDto(event.getId(), event.getName(), event.getUrl(), event.getImage()
                , event.getDate(), event.getSegment(), event.getSubsegment(), event.getPriceInPoints());
        return eventDto;
    }

    public static List<EventDto> mapToEventDtoList(List<Event> eventList){
        return eventList.stream().map(e -> mapToEventDto(e)).collect(Collectors.toList());
    }

    
}
