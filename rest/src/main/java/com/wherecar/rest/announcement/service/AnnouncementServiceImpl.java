package com.wherecar.rest.announcement.service;

import com.wherecar.rest.announcement.domain.Announcement;
import com.wherecar.rest.announcement.dto.AnnouncementDetailResponse;
import com.wherecar.rest.announcement.dto.AnnouncementRegisterRequest;
import com.wherecar.rest.announcement.dto.AnnouncementUpdateRequest;
import com.wherecar.rest.announcement.dto.AnnouncementsResponse;
import com.wherecar.rest.announcement.repository.AnnouncementRepository;
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

    private final AnnouncementRepository announcementRepository;

    @Override
    public void createAnnouncement(AnnouncementRegisterRequest announcementRegisterRequest) {

        // 요청 데이터를 엔터티로 변환
        Announcement announcement = Announcement.builder()
                .title(announcementRegisterRequest.getTitle())
                .content(announcementRegisterRequest.getContent())
                .build();

        // 요청 데이터 저장
        announcementRepository.save(announcement);
    }

    @Override
    public Page<AnnouncementsResponse> getAnnouncements(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Announcement> announcements = announcementRepository.findAnnouncements(pageRequest);

        return announcements.map(announcement -> AnnouncementsResponse.builder()
                .announcementId(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build()
        );
    }

    @Override
    public AnnouncementDetailResponse getAnnouncementDetail(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공지 사항입니다."));

        return AnnouncementDetailResponse.builder()
                .announcementId(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build();
    }

    @Override
    public void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest announcementUpdateRequest) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공지 사항입니다."));

        // 공지 사항 수정
        announcement.changeTitle(announcementUpdateRequest.getTitle());
        announcement.changeContent(announcementUpdateRequest.getContent());

        announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        if (!announcementRepository.existsById(announcementId)) {
            throw new RuntimeException("존재하지 않는 공지 사항입니다.");
        }
        announcementRepository.deleteById(announcementId);
    }
}
