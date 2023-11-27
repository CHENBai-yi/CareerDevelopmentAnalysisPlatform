package com.example.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.db.domain.pojo.DatabaseConnection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (DatabaseConnection)表数据库访问层
 *
 * @author ChenBaiYi
 * @since 2023-11-27 18:58:40
 */
@Mapper
public interface DatabaseConnectionDao extends BaseMapper<DatabaseConnection> {
    /**
     * @param databaseConnection 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * @description 分页查询数据
     * @date 2023-11-27 18:58:40
     */
    List<DatabaseConnection> selectDatabaseConnectionList(DatabaseConnection databaseConnection);

    // 测试多数元配置，重mysql test数据库查询
    List<String> fromTestDb();
}


