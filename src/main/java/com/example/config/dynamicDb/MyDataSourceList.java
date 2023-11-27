package com.example.config.dynamicDb;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Author:XY
 * PACkAGE:com.example.config.dynamicDb
 * Date:2023/11/27 13:38
 *
 * @author BaiYiChen
 */
public class MyDataSourceList extends AbstractRoutingDataSource {

    public MyDataSourceList(DataSource defaultTargetDataSource, Map<Object, Object> dataSourceMap) {
        setTargetDataSources(dataSourceMap);
        setDefaultTargetDataSource(defaultTargetDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DbThreadLocalContextHolder.getDbUse();
    }


}
