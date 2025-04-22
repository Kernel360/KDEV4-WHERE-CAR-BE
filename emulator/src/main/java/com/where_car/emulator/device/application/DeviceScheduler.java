package com.where_car.emulator.device.application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   디바이스 스케줄러 서비스 클래스
 *   에뮬레이터의 스케줄링 작업을 관리합니다.
 * </pre>
 *
 * @since 2025-03-30
 * @version 1.0
 */
@Slf4j
@Service
public class DeviceScheduler {

  private ScheduledExecutorService scheduler;

  public void startScheduler(Runnable task) {
    if (scheduler == null || scheduler.isShutdown()) {
      log.info("스케줄러를 시작합니다");
      scheduler = Executors.newScheduledThreadPool(2);
      scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
  }

  public void stopScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      log.info("스케줄러를 중지합니다");
      scheduler.shutdown();
      try {
        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
          scheduler.shutdownNow();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        scheduler.shutdownNow();
      }
    }
  }
}
