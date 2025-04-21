package com.wherecar.rest.common.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.wherecar.rest.user",
                "com.wherecar.rest.car",
                "com.wherecar.rest.carlog",
                "com.wherecar.rest.geoinfo",
                "com.wherecar.rest.geolog",
                "com.wherecar.rest.gpslog",
                "com.wherecar.rest.company",
                "com.wherecar.rest.announcement",
                "com.wherecar.rest.security",
                "com.wherecar.rest.websocket",
                "com.wherecar.rest.common"
        },
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager"
)
public class MainDataSourceConfig {

    @Primary
    @Bean(name = "mainDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.maindb")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mainDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(
                        "com.wherecar.rest.user",
                        "com.wherecar.rest.car",
                        "com.wherecar.rest.carlog",
                        "com.wherecar.rest.geoinfo",
                        "com.wherecar.rest.geolog",
                        "com.wherecar.rest.gpslog",
                        "com.wherecar.rest.company",
                        "com.wherecar.rest.announcement",
                        "com.wherecar.rest.security",
                        "com.wherecar.rest.websocket",
                        "com.wherecar.rest.common"
                ) // carlogsummary 만 제외
                .persistenceUnit("main")
                .build();
    }

    @Primary
    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}

