package nuc.onlineeducation.exchange.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author Ji YongGuang.
 * @date 17:42 2018/1/9.
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @SuppressWarnings("ContextJavaBeanUnresolvedMethodsInspection")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.hikari.jdbc-url"));
        hikariDataSource.setUsername(environment.getProperty("spring.datasource.hikari.username"));
        hikariDataSource.setPassword(environment.getProperty("spring.datasource.hikari.password"));
        hikariDataSource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.hikari" +
                ".maximum-pool-size")));
        hikariDataSource.setMinimumIdle(Integer.parseInt(environment.getProperty("spring.datasource.hikari" +
                ".minimum-idle")));
        hikariDataSource.setConnectionTestQuery(environment.getProperty("spring.datasource.hikari" +
                ".connection-test-query"));
        hikariDataSource.setIdleTimeout(Long.parseLong(environment.getProperty("spring.datasource.hikari" +
                ".idle-timeout")));
        hikariDataSource.setMaxLifetime(Long.parseLong(environment.getProperty("spring.datasource.hikari" +
                ".max-lifetime")));
        hikariDataSource.setConnectionTimeout(Long.parseLong(environment.getProperty("spring.datasource.hikari" +
                ".connection-timeout")));
        return hikariDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }
}
