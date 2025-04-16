package com.wherecar.rest.announcement.application;

import com.wherecar.rest.announcement.application.dto.AnnouncementDetailResponse;
import com.wherecar.rest.announcement.application.dto.AnnouncementRegisterRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementUpdateRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementsResponse;
import org.springframework.data.domain.Page;

public interface AnnouncementService {

    void createAnnouncement(AnnouncementRegisterRequest announcementRegisterRequest);

    Page<AnnouncementsResponse> getAnnouncements(int page, int size);

    AnnouncementDetailResponse getAnnouncementDetail(Long announcementId);

    void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest announcementUpdateRequest);

    void deleteAnnouncement(Long announcementId);
}
