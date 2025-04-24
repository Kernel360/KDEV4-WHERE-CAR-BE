package com.wherecar.rest.announcement.presentation;

import com.wherecar.rest.announcement.application.AnnouncementService;
import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.application.dto.AnnouncementResponse;
import com.wherecar.rest.common.constants.PaginationConstants;
import com.wherecar.rest.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Todo: 권한 체크 추후 추가 예정

@Slf4j
@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    // 공지 사항 등록
    @PostMapping
    public ResponseEntity<BaseResponse<AnnouncementResponse>> announcementCreate(@RequestBody AnnouncementRequest announcementRequest) {
        AnnouncementResponse announcementResponse = announcementService.createAnnouncement(announcementRequest);

        return BaseResponse.created(announcementResponse);
    }

    // 공지 사항 목록 조회
    @GetMapping
    public ResponseEntity<BaseResponse<Page<AnnouncementResponse>>> announcementsGet(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size)
    {
        Page<AnnouncementResponse> announcements = announcementService.getAnnouncements(page, size);

        return BaseResponse.ok(announcements);
    }

    // 공지 사항 글 조회
    @GetMapping("/{announcementId}")
    public ResponseEntity<BaseResponse<AnnouncementResponse>> announcementGetDetail(@PathVariable Long announcementId) {
        AnnouncementResponse announcement = announcementService.getAnnouncementDetail(announcementId);

        return BaseResponse.ok(announcement);
    }

    // 공지 사항 수정
    @PutMapping("/{announcementId}")
    public ResponseEntity<BaseResponse<AnnouncementResponse>> announcementUpdate(@PathVariable Long announcementId, @RequestBody AnnouncementRequest announcementRequest) {
        AnnouncementResponse announcementResponse = announcementService.updateAnnouncement(announcementId, announcementRequest);

        return BaseResponse.created(announcementResponse);
    }

    // 공지 사항 삭제
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<BaseResponse<Void>> announcementDelete(@PathVariable Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);

        return BaseResponse.ok();
    }

}
