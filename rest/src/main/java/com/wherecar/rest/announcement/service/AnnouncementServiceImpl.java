package com.wherecar.rest.announcement.service;

import com.wherecar.rest.announcement.domain.Announcement;
import com.wherecar.rest.announcement.domain.AnnouncementType;
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

        AnnouncementType announcementType;

        try {
            announcementType = AnnouncementType.valueOf(announcementRegisterRequest.getAnnouncementType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("잘못된 공지 사항입니다.");
        }

        Announcement announcement = Announcement.builder()
                .title(announcementRegisterRequest.getTitle())
                .content(announcementRegisterRequest.getContent())
                .announcementType(announcementType)
                .build();

        // 요청 데이터 저장
        announcementRepository.save(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementsResponse> getAnnouncements(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Announcement> announcements = announcementRepository.findAnnouncements(pageRequest);

        return announcements.map(announcement -> AnnouncementsResponse.builder()
                .announcementId(announcement.getId())
                .announcementType(announcement.getAnnouncementType().toString())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AnnouncementDetailResponse getAnnouncementDetail(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공지 사항입니다."));

        return AnnouncementDetailResponse.builder()
                .announcementId(announcement.getId())
                .announcementType(announcement.getAnnouncementType().toString())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build();
    }

    @Override
    public void updateAnnouncement(Long announcementId, AnnouncementUpdateRequest announcementUpdateRequest) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 공지 사항입니다."));

        AnnouncementType announcementType;

        try {
            announcementType = AnnouncementType.valueOf(announcementUpdateRequest.getAnnouncementType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("잘못된 공지 사항입니다.");
        }


        // 공지 사항 수정
        announcement.changeTitle(announcementUpdateRequest.getTitle());
        announcement.changeContent(announcementUpdateRequest.getContent());
        announcement.changeAnnouncementType(announcementType);

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
