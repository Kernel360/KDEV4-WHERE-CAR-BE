package com.wherecar.rest.geo.dto.emulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControlResponse {
    private String ctrId;  // 제어 아이디
    private String ctrCd;  // 제어 코드
    private String ctrVal; // 제어 값
}

