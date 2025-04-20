package com.wherecar.collector.car.infrastructure;

import com.wherecar.collector.car.domain.CarStatus;
import com.wherecar.collector.common.constant.CarState;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
    Optional<CarStatus> findByCarId(Long carId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs FROM CarStatus cs WHERE cs.car.id = :carId")
    Optional<CarStatus> findByCarIdForUpdate(@Param("carId") Long carId);

    @Modifying
    @Query("UPDATE CarStatus cs SET cs.batteryVoltage = :batteryVoltage WHERE cs.car.id = :carId")
    void updateBatteryVoltage(@Param("carId") Long carId, @Param("batteryVoltage") Integer batteryVoltage);

    @Modifying
    @Query("UPDATE CarStatus cs SET cs.mileage = :mileage WHERE cs.car.id = :carId")
    void updateMileage(@Param("carId") Long carId, @Param("mileage") Double mileage);


    @Modifying
    @Query("UPDATE CarStatus cs SET cs.carState = :carState WHERE cs.car.id = :carId")
    void updateCarState(@Param("carId") Long carId, @Param("carState") CarState carState);
}
