package com.wherecar.rest.announcement.domain;

import com.wherecar.rest.announcement.application.dto.AnnouncementRequest;
import com.wherecar.rest.announcement.domain.constant.AnnouncementType;
import com.wherecar.rest.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "announcements")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="announcement_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="announcement_type")
    private AnnouncementType announcementType;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    public void updateAnnouncement(AnnouncementRequest announcementRequest) {
        this.title = announcementRequest.getTitle();
        this.content = announcementRequest.getContent();
        this.announcementType = announcementRequest.getAnnouncementType();
    }

}
