package com.wherecar.collector.repository;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.OnOffLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnOffLogRepository extends JpaRepository<OnOffLog, Long> {

    // TODO 직전 시동 OFF일 때의 OnOffLog를 찾는 쿼리가 맞는지 확인하기
    Optional<OnOffLog> findTopByCarIdOrderByOffTimeDesc(Long carId);

    // TODO 직전 시동 ON일 때의 OnOffLog를 찾는 쿼리가 맞는지 확인하기
    Optional<OnOffLog> findTopByCarIdOrderByOnTimeDesc(Long carId);
}
