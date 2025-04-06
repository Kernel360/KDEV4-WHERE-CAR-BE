package com.wherecar.rest.announcement.domain;

import com.wherecar.rest.domain.BaseEntity;
import com.wherecar.rest.domain.CarStatus;
import com.wherecar.rest.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "announcements")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="announcement_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

}
