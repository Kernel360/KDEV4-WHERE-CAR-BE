package com.wherecar.rest.announcement.application.dto;

import com.wherecar.rest.announcement.domain.constant.AnnouncementType;
import lombok.*;

@Setter
@Getter
@ToString
public class AnnouncementRequest {

    private String title;

    private String content;

    private AnnouncementType announcementType;
}
