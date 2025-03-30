package com.where_car.emulator.device.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *   에뮬레이터 도메인
 * isOn: 시동 on/off 상태
 * </pre>
 * @author Changil.kim
 * @version 1.0
 * @since 2025-03-28
 */

@Getter
@Setter
@Component
public class DeviceEntity {

  private boolean deviceStatus;
}
