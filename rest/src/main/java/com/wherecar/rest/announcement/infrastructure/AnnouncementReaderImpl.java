package com.wherecar.rest.announcement.infrastructure;

import com.wherecar.rest.announcement.domain.Announcement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnouncementReaderImpl implements AnnouncementReader {

    private final AnnouncementRepository announcementRepository;

    @Override
    public Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(()-> new RuntimeException("No announcement found with id " + announcementId));
    }

    @Override
    public Page<Announcement> getAnnouncementPage(Pageable pageable) {
        return announcementRepository.findAnnouncements(pageable);
    }
}
