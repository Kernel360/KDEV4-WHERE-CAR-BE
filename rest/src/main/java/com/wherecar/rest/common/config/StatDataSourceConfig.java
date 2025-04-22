package com.wherecar.rest.common.config;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.wherecar.rest.carlogsummary",
        entityManagerFactoryRef = "statEntityManagerFactory",
        transactionManagerRef = "statTransactionManager"
)
public class StatDataSourceConfig {

    @Bean(name = "statDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.statdb")
    public DataSource statDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "statEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean statEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("statDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.wherecar.rest.carlogsummary")
                .persistenceUnit("stat")
                .build();
    }

    @Bean(name = "statTransactionManager")
    public PlatformTransactionManager statTransactionManager(
            @Qualifier("statEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
