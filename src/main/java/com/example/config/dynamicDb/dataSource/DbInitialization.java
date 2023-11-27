package com.example.config.dynamicDb.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.config.dynamicDb.MyDataSourceList;
import com.example.db.domain.dto.DatabaseConnectionDto;
import com.example.db.domain.mapper.DbSourceToDruidDataSource;
import com.example.db.domain.pojo.DatabaseConnection;
import com.example.db.service.DatabaseConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.dynamicDb.dataSource
 * Date:2023/11/27 19:13
 * EMAIL:
 *
 * @author BaiYiChen
 */
@Configuration
@RequiredArgsConstructor
public class DbInitialization implements CommandLineRunner {
    private final DatabaseConnectionService databaseConnectionService;
    private final MyDataSourceList dataSourceList;
    private final Map<Object, Object> dataSourceMap = new HashMap<>();

    /**
     * @param args incoming main method arguments
     * @descrption 数据库信息配置多数据源
     */
    @Override
    public void run(final String... args) {
        Optional.ofNullable(databaseConnectionService.findDatabaseConnectionSelectList(DatabaseConnectionDto.builder().build()))
                .ifPresent(databaseConnections -> {
                    final Map<Object, Object> dataSourceMap = databaseConnections.stream()
                            .collect(Collectors.toMap(DatabaseConnection::getKey, connection -> {
                                final DruidDataSource druidDataSource = DbSourceToDruidDataSource.DB_SOURCE_TO_DRUID_DATA_SOURCE.toDruidDataSource(connection);
                                druidDataSource.setDriverClassName(connection.getDriver());
                                druidDataSource.setDefaultAutoCommit(true);
                                druidDataSource.setAsyncInit(true);
                                druidDataSource.setKillWhenSocketReadTimeout(true);
                                druidDataSource.setValidationQuery("select 1;");
                                return druidDataSource;
                            }));
                    final Map<Object, DataSource> resolvedDataSources = dataSourceList.getResolvedDataSources();
                    dataSourceMap.putAll(resolvedDataSources);
                    dataSourceList.setTargetDataSources(dataSourceMap);
                    dataSourceList.afterPropertiesSet();// 重新解析数据源数量
                });
    }
}
