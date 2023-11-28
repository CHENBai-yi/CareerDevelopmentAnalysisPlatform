package com.example;

import cn.licoy.encryptbody.annotation.EnableEncryptBody;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.example.config.dynamicDb.MyDataSourceList;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Author:XY
 * PACkAGE:com.example.chart
 * Date:2023/11/23 15:36
 *
 * @author BaiYiChen
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableEncryptBody
public class ChartApplication {

    public static void main(String[] args) {

        SpringApplication.run(ChartApplication.class, args);

    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据库类型是MySql，因此参数填写DbType.MYSQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public PooledDataSource mySql() {
        return new PooledDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.sqlite")
    public SQLiteConnectionPoolDataSource sqlite() {
        return new SQLiteConnectionPoolDataSource();
    }

    @Bean
    @Primary
    public MyDataSourceList myDataSourceList(@Qualifier("mySql") DataSource mysql, @Qualifier("sqlite") DataSource sqlite) {
        final HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("mysql", mysql);
        dataSourceMap.put("sqlite", sqlite);
        return new MyDataSourceList(mysql, dataSourceMap);
    }


}
