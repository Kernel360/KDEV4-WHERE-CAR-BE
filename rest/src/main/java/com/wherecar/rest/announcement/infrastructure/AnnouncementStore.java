package com.wherecar.rest.announcement.infrastructure;

import com.wherecar.rest.announcement.domain.Announcement;

public interface AnnouncementStore {
    Announcement store(Announcement announcement);
    void delete(Long announcementId);
}
