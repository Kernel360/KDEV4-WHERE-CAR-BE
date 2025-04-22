package com.wherecar.collector.carlog.infrastructure;

import com.wherecar.collector.carlog.domain.CarLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarLogRepository extends JpaRepository<CarLog, Long> {

    // TODO 직전 시동 OFF일 때의 CarLog를 찾는 쿼리가 맞는지 확인하기
    Optional<CarLog> findTopByMdnOrderByOffTimeDesc(String mdn);

    // TODO 직전 시동 ON일 때의 CarLog를 찾는 쿼리가 맞는지 확인하기
    Optional<CarLog> findTopByMdnOrderByOnTimeDesc(String mdn);
}
