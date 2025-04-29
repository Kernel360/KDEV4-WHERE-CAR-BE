package com.wherecar.hub.carlog.persentation;

import com.wherecar.hub.carlog.application.CarLogHubService;
import com.wherecar.hub.carlog.application.dto.CarLogRequest;
import com.wherecar.hub.carlog.application.dto.CarLogResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CarLogResponse> sendOnLogMessage(@RequestBody @Valid CarLogRequest onLogRequest) {
        carLogHubService.sendCarOnLogMessage(onLogRequest);

        return ResponseEntity.ok(CarLogResponse.getCarLogResponse(onLogRequest.getMdn()));

    }

    @PostMapping("/off")
    public ResponseEntity<CarLogResponse> sendOffLogMessage(@RequestBody @Valid CarLogRequest offLogRequest) {
        carLogHubService.sendCarOffLogMessage(offLogRequest);

        return ResponseEntity.ok(CarLogResponse.getCarLogResponse(offLogRequest.getMdn()));
    }

}
