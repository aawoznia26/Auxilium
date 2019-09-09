package com.rest.auxilium.dto.kaufland;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KauflandOfferDto{

    private String offerId;
    private String title;
    private String subtitle;
    private Double price;
    private String label;
    private String listImage;


}
