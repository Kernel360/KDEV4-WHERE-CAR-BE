package com.wherecar.hub.carlog.persentation;

import com.wherecar.hub.carlog.application.CarLogHubService;
import com.wherecar.hub.carlog.application.dto.CarLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/hub")
@RequiredArgsConstructor
public class CarLogHubController {

    private final CarLogHubService carLogHubService;

    @PostMapping("/on")
    public void sendOnLogMessage(@RequestBody CarLogRequest onLogRequest) {
        carLogHubService.sendCarOnLogMessage(onLogRequest);

        // todo: return 수정예정 -> redis 토큰 검증 결과
    }

    @PostMapping("/off")
    public void sendOffLogMessage(@RequestBody CarLogRequest offLogRequest) {
        carLogHubService.sendCarOffLogMessage(offLogRequest);

        // todo: reuturn 수정예정 -> redis 토큰 검증 결과
    }

}
