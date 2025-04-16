package com.wherecar.rest.announcement.infrastructure;

import com.wherecar.rest.announcement.domain.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a")
    Page<Announcement> findAnnouncements(Pageable pageable);
}
