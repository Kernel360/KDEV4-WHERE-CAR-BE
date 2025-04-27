package com.wherecar.rest.announcement.application.dto;

import com.wherecar.rest.announcement.domain.constant.AnnouncementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
public class AnnouncementRequest {

    @NotBlank(message = "title은 필수입니다.")
    private String title;

    @NotBlank(message = "content는 필수입니다.")
    private String content;

    @NotNull(message = "announcementType은 필수입니다.")
    private AnnouncementType announcementType;
}
