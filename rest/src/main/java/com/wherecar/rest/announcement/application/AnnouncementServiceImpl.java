package com.wherecar.rest.announcement.application;

import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementResponse;
import com.wherecar.rest.announcement.domain.Announcement;
import com.wherecar.rest.announcement.domain.AnnouncementFactory;
import com.wherecar.rest.announcement.infrastructure.AnnouncementReader;
import com.wherecar.rest.announcement.infrastructure.AnnouncementStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementFactory announcementFactory;
    private final AnnouncementStore announcementStore;
    private final AnnouncementReader announcementReader;

    @Override
    public AnnouncementResponse createAnnouncement(AnnouncementRequest announcementRequest) {

        Announcement announcement = announcementFactory.toAnnouncement(announcementRequest);
        announcement = announcementStore.store(announcement);
        return announcementFactory.toAnnouncementResponse(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementResponse> getAnnouncements(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Announcement> announcementPage = announcementReader.getAnnouncementPage(pageRequest);

        return announcementFactory.toAnnouncementResponsePage(announcementPage);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncementDetail(Long announcementId) {
        Announcement announcement = announcementReader.getAnnouncementById(announcementId);

        return announcementFactory.toAnnouncementResponse(announcement);
    }

    @Override
    public AnnouncementResponse updateAnnouncement(Long announcementId, AnnouncementRequest announcementRequest) {
        Announcement announcement = announcementReader.getAnnouncementById(announcementId);
        announcement.updateAnnouncement(announcementRequest);
        announcement = announcementStore.store(announcement);
        return announcementFactory.toAnnouncementResponse(announcement);
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        announcementStore.delete(announcementId);
    }
}
