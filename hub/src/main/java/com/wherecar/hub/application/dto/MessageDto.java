package com.wherecar.hub.application.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String title;
    private String message;
}
