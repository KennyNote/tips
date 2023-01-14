package com.learning.notebook.tips.db.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {"com.learning.notebook.db.mapper"}, sqlSessionFactoryRef = "mySQLSqlSessionFactory")
public class MySQLDataSourceConfig {

    private MySQLDataSourceProperties mySQLDataSourceProperties;

    @Autowired
    public void setMySQLDataSourceProperties(MySQLDataSourceProperties mySQLDataSourceProperties) {
        this.mySQLDataSourceProperties = mySQLDataSourceProperties;
    }

    @Primary
    @Bean(name = "mySQLDataSource")
    public DataSource mySQLDataSource() {
        DataSourceBuilder<?> mySQLDataSourceBuilder = DataSourceBuilder.create();
        mySQLDataSourceBuilder.url(mySQLDataSourceProperties.getUrl());
        mySQLDataSourceBuilder.username(mySQLDataSourceProperties.getUsername());
        mySQLDataSourceBuilder.password(mySQLDataSourceProperties.getPassword());
        mySQLDataSourceBuilder.driverClassName(mySQLDataSourceProperties.getDriverClassName());
        return mySQLDataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "mySQLSqlSessionFactory")
    public SqlSessionFactory mySQLSqlSessionFactory(@Qualifier("mySQLDataSource") DataSource datasource)
        throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasource);
        factoryBean.setTypeAliasesPackage("com.learning.notebook.db.transaction.entity");
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        factoryBean.setConfigLocation(
//            new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "mySQLTransactionManager")
    public DataSourceTransactionManager mySQLTransactionManager(@Qualifier("mySQLDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "mySQLSqlSessionTemplate")
    public SqlSessionTemplate mySQLSqlSessionTemplate(
        @Qualifier("mySQLSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
