package com.wherecar.rest.announcement.application.dto;

import com.wherecar.rest.announcement.domain.constant.AnnouncementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRequest {

    private String title;

    private String content;

    private AnnouncementType announcementType;
}
