package com.wherecar.batch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.wherecar.batch.stat.infrastructure",
        entityManagerFactoryRef = "statEntityManagerFactory",
        transactionManagerRef = "statTransactionManager"
)
public class StatDataSourceConfig {
    @Bean(name = "statDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-stat")
    public DataSource statDataSource() {

        return DataSourceBuilder.create()
                .build();
    }

    @Bean(name = "statEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean statEntityManagerFactory(
            @Qualifier("statDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(dataSource)
                .packages("com.wherecar.batch.stat.domain")
                .persistenceUnit("stat")
                .build();
    }

    @Bean(name = "statTransactionManager")
    public PlatformTransactionManager statTransactionManager(
            @Qualifier("statEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

