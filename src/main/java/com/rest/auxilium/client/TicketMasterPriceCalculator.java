package com.rest.auxilium.client;

import com.rest.auxilium.dto.ticketMaster.TicketMasterClassificationDto;
import com.rest.auxilium.dto.ticketMaster.TicketMasterDatesDto;

public interface TicketMasterPriceCalculator {

    int calculatePrice(TicketMasterClassificationDto ticketMasterClassificationDto, TicketMasterDatesDto ticketMasterDatesDto);
}
