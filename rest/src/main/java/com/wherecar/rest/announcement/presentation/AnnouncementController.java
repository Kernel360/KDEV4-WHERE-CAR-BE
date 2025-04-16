package com.wherecar.rest.announcement.presentation;

import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementResponse;
import com.wherecar.rest.announcement.application.AnnouncementService;
import com.wherecar.rest.common.constants.PaginationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    // 공지 사항 등록
    @PostMapping
    public ResponseEntity<Void> announcementCreate(@RequestBody AnnouncementRequest announcementRequest) {
        announcementService.createAnnouncement(announcementRequest);

        return ResponseEntity.ok().build();
    }

    // 공지 사항 목록 조회
    @GetMapping
    public ResponseEntity<Page<AnnouncementResponse>> announcementsGet(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size)
    {
        Page<AnnouncementResponse> announcements = announcementService.getAnnouncements(page, size);

        return ResponseEntity.ok(announcements);
    }

    // 공지 사항 글 조회
    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> announcementGetDetail(@PathVariable Long announcementId) {
        AnnouncementResponse announcement = announcementService.getAnnouncementDetail(announcementId);

        return ResponseEntity.ok(announcement);
    }

    // 공지 사항 수정
    @PutMapping("/{announcementId}")
    public ResponseEntity<Void> announcementUpdate(@PathVariable Long announcementId, @RequestBody AnnouncementRequest announcementRequest) {
        announcementService.updateAnnouncement(announcementId, announcementRequest);

        return ResponseEntity.ok().build();
    }

    // 공지 사항 삭제
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> announcementDelete(@PathVariable Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);

        return ResponseEntity.ok().build();
    }

}
