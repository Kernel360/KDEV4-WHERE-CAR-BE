package com.wherecar.rest.announcement.domain;

import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AnnouncementFactory {
    public Announcement toAnnouncement(AnnouncementRequest announcementRequest) {
        return Announcement.builder()
                .title(announcementRequest.getTitle())
                .content(announcementRequest.getContent())
                .announcementType(announcementRequest.getAnnouncementType())
                .build();
    }

    public AnnouncementResponse toAnnouncementResponse(Announcement announcement) {
        return AnnouncementResponse.builder()
                .announcementId(announcement.getId())
                .announcementType(announcement.getAnnouncementType().toString())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build();
    }

    public Page<AnnouncementResponse> toAnnouncementResponsePage(Page<Announcement> announcementPage) {
        return announcementPage.map(announcement -> AnnouncementResponse.builder()
                .announcementId(announcement.getId())
                .announcementType(announcement.getAnnouncementType().toString())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build());
    }
}
