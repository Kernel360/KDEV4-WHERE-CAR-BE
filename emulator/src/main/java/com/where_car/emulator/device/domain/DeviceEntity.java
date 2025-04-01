package com.where_car.emulator.device.domain;

import lombok.Builder;
import lombok.Getter;

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
@Builder
public class DeviceEntity {

  private boolean isOn;
}
