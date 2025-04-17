package com.wherecar.rest.announcement.application;

import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementResponse;
import org.springframework.data.domain.Page;

public interface AnnouncementService {

    AnnouncementResponse createAnnouncement(AnnouncementRequest announcementRequest);

    Page<AnnouncementResponse> getAnnouncements(int page, int size);

    AnnouncementResponse getAnnouncementDetail(Long announcementId);

    AnnouncementResponse updateAnnouncement(Long announcementId, AnnouncementRequest announcementRequest);

    void deleteAnnouncement(Long announcementId);
}
