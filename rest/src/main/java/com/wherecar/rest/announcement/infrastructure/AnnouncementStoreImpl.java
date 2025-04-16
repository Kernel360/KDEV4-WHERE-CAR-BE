package com.wherecar.rest.announcement.infrastructure;

import com.wherecar.rest.announcement.domain.Announcement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnouncementStoreImpl implements AnnouncementStore {
    private final AnnouncementRepository announcementRepository;

    @Override
    public Announcement store(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public void delete(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }
}
