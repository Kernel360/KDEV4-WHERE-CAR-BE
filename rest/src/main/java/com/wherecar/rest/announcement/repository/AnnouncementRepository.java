package com.wherecar.rest.announcement.repository;

import com.wherecar.rest.announcement.domain.Announcement;
import com.wherecar.rest.domain.CarLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a")
    Page<Announcement> findAnnouncements(Pageable pageable);
}
