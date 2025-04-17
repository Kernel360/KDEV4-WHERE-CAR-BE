package com.wherecar.rest.announcement.infrastructure;

import com.wherecar.rest.announcement.domain.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementReader {
    Announcement getAnnouncementById(Long announcementId);
    Page<Announcement> getAnnouncementPage(Pageable pageable);
}
