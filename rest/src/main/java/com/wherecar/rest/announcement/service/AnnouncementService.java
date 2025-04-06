package com.wherecar.rest.announcement.service;

import com.wherecar.rest.announcement.dto.AnnouncementDetailResponse;
import com.wherecar.rest.announcement.dto.AnnouncementRegisterRequest;
import com.wherecar.rest.announcement.dto.AnnouncementUpdateRequest;
import com.wherecar.rest.announcement.dto.AnnouncementsResponse;
import org.springframework.data.domain.Page;

public interface AnnouncementService {

    void createAnnouncement(AnnouncementRegisterRequest announcementRegisterRequest);

    Page<AnnouncementsResponse> getAnnouncements(int page, int size);

    AnnouncementDetailResponse getAnnouncementDetail(Long announcementId);

    void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest announcementUpdateRequest);

    void deleteAnnouncement(Long announcementId);
}
