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

        log.info("[Announcement][AnnouncementServiceImpl][createAnnouncement] 시작 | announcementRequest = {}", announcementRequest);
        Announcement announcement = announcementFactory.toAnnouncement(announcementRequest);
        announcement = announcementStore.store(announcement);
        AnnouncementResponse announcementResponse = announcementFactory.toAnnouncementResponse(announcement);
        log.info("[Announcement][AnnouncementServiceImpl][createAnnouncement] 끝 | announcement = {}", announcementResponse);

        return announcementResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementResponse> getAnnouncements(int page, int size) {
        log.info("[Announcement][AnnouncementServiceImpl][getAnnouncements] 시작 | page = {}, size = {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Announcement> announcementPage = announcementReader.getAnnouncementPage(pageRequest);

        Page<AnnouncementResponse> announcementResponsePage = announcementFactory.toAnnouncementResponsePage(announcementPage);
        log.info("[Announcement][AnnouncementServiceImpl][getAnnouncements] 끝 | announcementPage = {}", announcementResponsePage);

        return announcementResponsePage;
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncementDetail(Long announcementId) {
        log.info("[Announcement][AnnouncementServiceImpl][getAnnouncementDetail] 시작 | announcementId = {}", announcementId);
        Announcement announcement = announcementReader.getAnnouncementById(announcementId);
        AnnouncementResponse announcementResponse = announcementFactory.toAnnouncementResponse(announcement);
        log.info("[Announcement][AnnouncementServiceImpl][getAnnouncementDetail] 끝 | announcement = {}", announcementResponse);

        return announcementResponse;
    }

    @Override
    public AnnouncementResponse updateAnnouncement(Long announcementId, AnnouncementRequest announcementRequest) {
        log.info("[Announcement][AnnouncementServiceImpl][updateAnnouncement] 시작 | announcementId = {}, announcementRequest = {}", announcementId, announcementRequest);
        Announcement announcement = announcementReader.getAnnouncementById(announcementId);
        announcement.updateAnnouncement(announcementRequest);
        announcement = announcementStore.store(announcement);
        AnnouncementResponse announcementResponse = announcementFactory.toAnnouncementResponse(announcement);
        log.info("[Announcement][AnnouncementServiceImpl][updateAnnouncement] 끝 | announcement = {}", announcementResponse);

        return announcementResponse;
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        log.info("[Announcement][AnnouncementServiceImpl][deleteAnnouncement] 시작 | announcementId = {}", announcementId);
        announcementStore.delete(announcementId);
        log.info("[Announcement][AnnouncementServiceImpl][deleteAnnouncement] 끝");
    }
}
