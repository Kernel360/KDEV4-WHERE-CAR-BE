package com.wherecar.rest.carlog.infrastructure.querydsl;


import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.wherecar.rest.car.domain.QCar;
import com.wherecar.rest.carlog.domain.CarLog;
import com.wherecar.rest.carlog.domain.QCarLog;
import com.wherecar.rest.carlog.domain.constant.DriveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

public class CarLogSearchImpl extends QuerydslRepositorySupport implements CarLogSearch {
    public CarLogSearchImpl() {
        super(CarLog.class);
    }

    public Page<CarLog> searchCarLogWithFilter(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, DriveType driveType, Pageable pageable) {
        QCarLog carLog = QCarLog.carLog;
        QCar car = QCar.car;

        JPQLQuery<CarLog> query = from(carLog).select(carLog);

        BooleanBuilder builder = new BooleanBuilder();

        if (companyId != null) {
            builder.and(
                    JPAExpressions.selectOne()
                            .from(car)
                            .where(car.mdn.eq(carLog.mdn)
                                    .and(car.company.id.eq(companyId)))
                            .exists()
            );
        }

        if (mdn != null && !mdn.isBlank()) {
            builder.and(carLog.mdn.eq(mdn));
        }

        if (startTime != null) {
            builder.and(carLog.onTime.goe(startTime));
        }

        if (endTime != null) {
            builder.and(carLog.offTime.loe(endTime));
        }

        if (driveType != null) {
            builder.and(carLog.driveType.eq(driveType));
        }

        query.where(builder);
        query.orderBy(carLog.offTime.desc());

        JPQLQuery<CarLog> pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<CarLog> content = pagedQuery.fetch();
        long count = pagedQuery.fetchCount();
        return new PageImpl<>(content, pageable, count);
    }
}
