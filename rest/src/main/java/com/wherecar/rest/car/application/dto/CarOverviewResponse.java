package com.wherecar.rest.car.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarOverviewResponse {
    private long totalCars;
    private long totalCorporateCars;
    private long totalPrivateCars;
    private long activeCars;
    private long inactiveCars;
    private long untrackedCars;
}
