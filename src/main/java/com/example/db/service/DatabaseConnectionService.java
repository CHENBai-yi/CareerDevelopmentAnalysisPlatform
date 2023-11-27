package com.example.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.db.domain.dto.DatabaseConnectionDto;
import com.example.db.domain.pojo.DatabaseConnection;

import java.util.List;

/**
 * (DatabaseConnection)表服务接口
 *
 * @author ChenBaiYi
 * @since 2023-11-27 18:58:40
 */
public interface DatabaseConnectionService extends IService<DatabaseConnection> {

    /**
     * @param id 主键
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description  通过ID查询单条数据
     * &#064;date 2023-11-27 18:58:40
     */
    DatabaseConnection selectDatabaseConnectionById(final Integer id);

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description 分页查询数据
     * &#064;date 2023-11-27 18:58:40
     */
    List<DatabaseConnection> findDatabaseConnectionSelectList(final DatabaseConnectionDto databaseConnectiondto);

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description  新增数据
     * &#064;date 2023-11-27 18:58:40
     */
    int insert(final DatabaseConnectionDto databaseConnectiondto);

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description 修改数据并返回
     * &#064;date 2023-11-27 18:58:40
     */
    int update(final DatabaseConnectionDto databaseConnectiondto);

    /**
     * @param ids 主键
     * @return
     * @author ChenBaiYi
     * &#064;description  通过主键删除数据
     * &#064;date 2023-11-27 18:58:40
     */
    int deleteById(final List<Long> ids);
}
