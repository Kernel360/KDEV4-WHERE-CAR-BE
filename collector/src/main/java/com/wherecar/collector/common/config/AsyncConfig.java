//package com.wherecar.collector.common.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Slf4j
//@Configuration
//@EnableAsync
//public class AsyncConfig implements AsyncConfigurer {
//
////    @Bean
////    public Executor asyncExecutor() {
////        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
////
////        executor.setCorePoolSize(5);                 // 기본(코어) 스레드 수를 설정. 최소한으로 유지되는 스레드 수
////        executor.setMaxPoolSize(50);                 // 최대 스레드 수를 설정
////        executor.setQueueCapacity(100);              // 실행 대기 중인 작업을 담는 큐의 최대 크기. 코어 스레드가 모두 바쁘면, 작업은 이 큐에 쌓임
////        executor.setKeepAliveSeconds(120);           // 해당 초까지 idle 상태가 유지되면 스레드를 종료한다(idle:어떠한 동작 상태도 아닐 때)
////        executor.setAllowCoreThreadTimeOut(true);    // 기본(코어) 스레드도 idle 상태일 경우 제거할지 여부를 설정함. true로 설정하면 setKeepAliveSeconds() 설정이 코어 스레드에도 적용됨
////        executor.setPrestartAllCoreThreads(true);    // 모든 코어 스레드를 초기화 시점에 미리 시작할지 여부. true이면 Bean이 초기화되면서 스레드도 즉시 생성됨
////        executor.setWaitForTasksToCompleteOnShutdown(true);  // 스프링 종료 시 현재 진행 중이던 작업이 완료된 후 스레드를 종료시킬지에 대한 여부. true로 설정하면 작업이 끝날 때까지 기다림
////        executor.setAwaitTerminationSeconds(20);     // 스프링 종료 시 작업 완료를 기다리는 최대 시간(초) 위 설정이 true일 때만 유효
////        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());     // 작업이 너무 많아 더 이상 처리할 수 없을 때의 정책을 설정. AbortPolicy는 예외를 던져 작업을 거절함(기본값이며, 다른 정책들도 있음)
////        executor.setThreadNamePrefix("Async-Test");             // 스레드의 이름 지정
////        executor.initialize();                                  // 스레드 풀을 초기화. 설정이 완료된 후 사용 가능하게 만듦
////
////        return executor;
////    }
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new AsyncUncaughtExceptionHandler() {
//
//            @Override
//            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
//                log.error("비동기 메서드에서 예외 발생: method={}, message={}", method.getName(), ex.getMessage(), ex);
//            }
//        };
//    }
//
//}
