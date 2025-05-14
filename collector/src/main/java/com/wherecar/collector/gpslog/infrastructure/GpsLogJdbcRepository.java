package com.wherecar.collector.gpslog.infrastructure;

import com.wherecar.collector.gpslog.domain.GpsLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GpsLogJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL = """
        INSERT INTO gps_logs (mdn, timestamp, gps_condition, latitude, longitude, angle, speed, sum, created_at, updated_at)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public void batchInsert(List<GpsLog> logs) {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GpsLog log = logs.get(i);

                ps.setString(1, log.getMdn());
                ps.setTimestamp(2, Timestamp.valueOf(log.getTimestamp()));
                ps.setString(3, log.getGpsCondition().name());
                ps.setDouble(4, log.getLatitude());
                ps.setDouble(5, log.getLongitude());
                ps.setInt(6, log.getAngle());
                ps.setInt(7, log.getSpeed());
                ps.setInt(8, log.getSum());
                ps.setTimestamp(9, Timestamp.valueOf(now)); // createdAt
                ps.setTimestamp(10, Timestamp.valueOf(now)); // updatedAt
            }

            @Override
            public int getBatchSize() {
                return logs.size();
            }
        });
    }

}