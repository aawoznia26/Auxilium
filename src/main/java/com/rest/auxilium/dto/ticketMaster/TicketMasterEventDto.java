package com.rest.auxilium.dto.ticketMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketMasterEventDto{

    private String name;
    private String id;
    private String url;
    private List<TicketMasterImageDto> images;
    private TicketMasterDatesDto dates;
    private List<TicketMasterClassificationDto> classifications;



}
