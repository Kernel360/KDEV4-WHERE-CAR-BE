package com.wherecar.rest.announcement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementUpdateRequest {

    private String title;

    private String content;
}
