package com.example.db.domain.mapper;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.db.domain.pojo.DatabaseConnection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * PROJECT:chart
 * PACkAGE:com.example.db.domain
 * Date:2023/11/27 19:34
 * EMAIL:
 *
 * @author BaiYiChen
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DbSourceToDruidDataSource {
    DbSourceToDruidDataSource DB_SOURCE_TO_DRUID_DATA_SOURCE = Mappers.getMapper(DbSourceToDruidDataSource.class);

    @Mapping(target = "driver", ignore = true)
    DruidDataSource toDruidDataSource(DatabaseConnection databaseConnection);

    List<DruidDataSource> toDruidDataSourceAList(List<DatabaseConnection> databaseConnection);
}
