package com.where_car.emulator.device.application.dto;

import lombok.Getter;

@Getter
public class LocationDto {
	private final String departure;
	private final String destination;

	public LocationDto(String departure, String destination) {
		this.departure = departure;
		this.destination = destination;
	}
}
