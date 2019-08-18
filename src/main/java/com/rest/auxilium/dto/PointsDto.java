package com.rest.auxilium.dto;

import com.rest.auxilium.domain.PointStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointsDto {

    private Long id;

    private int value;

    private LocalDate expirationDate;

    private PointStatus pointStatus;

    private Long userId;

    public PointsDto(int value, Long userId) {
        this.value = value;
        this.userId = userId;
    }


    public PointsDto(Long id, int value, LocalDate expirationDate, PointStatus pointStatus) {
        this.id = id;
        this.value = value;
        this.expirationDate = expirationDate;
        this.pointStatus = pointStatus;
    }
}
