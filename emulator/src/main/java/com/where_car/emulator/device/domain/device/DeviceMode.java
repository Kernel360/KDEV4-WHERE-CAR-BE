package com.where_car.emulator.device.domain.device;

/**
 * 장치 작동 모드를 정의하는 열거형
 */
public enum DeviceMode {
    /**
     * 시뮬레이션 모드: GPX 파일을 사용하여 가상 경로를 따라 움직임
     */
    SIMULATION,
    
    /**
     * 실시간 모드: 실제 위치 데이터를 사용하여 움직임
     */
    REALTIME
}
