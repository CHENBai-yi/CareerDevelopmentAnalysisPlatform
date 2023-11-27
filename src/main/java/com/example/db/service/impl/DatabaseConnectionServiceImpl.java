package com.example.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.dao.DatabaseConnectionDao;
import com.example.db.domain.dto.DatabaseConnectionDto;
import com.example.db.domain.pojo.DatabaseConnection;
import com.example.db.service.DatabaseConnectionService;
import com.ruoyi.common.utils.bean.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (DatabaseConnection)表服务实现类
 *
 * @author ChenBaiYi
 * @since 2023-11-27 18:58:41
 */
@Service("databaseConnectionService")
@RequiredArgsConstructor
public class DatabaseConnectionServiceImpl extends ServiceImpl<DatabaseConnectionDao, DatabaseConnection> implements DatabaseConnectionService {

    private final DatabaseConnectionDao databaseConnectionDao;

    /**
     * @param id 主键
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  通过ID查询单条数据
     * &#064;date 2023-11-27 18:58:41
     */
    @Override
    public DatabaseConnection selectDatabaseConnectionById(Integer id) {
        LambdaQueryWrapper<DatabaseConnection> databaseConnectionlam = new LambdaQueryWrapper<DatabaseConnection>();
        databaseConnectionlam.eq(DatabaseConnection::getId, id);
        return baseMapper.selectOne(databaseConnectionlam);
    }

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  分页查询数据
     * &#064;date 2023-11-27 18:58:41
     */
    @Override
    public List<DatabaseConnection> findDatabaseConnectionSelectList(DatabaseConnectionDto databaseConnectiondto) {
        DatabaseConnection databaseConnection = DatabaseConnection.builder().build();
        BeanUtils.copyBeanProp(databaseConnection, databaseConnectiondto);
        return baseMapper.selectDatabaseConnectionList(databaseConnection);
    }

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  新增数据
     * &#064;date 2023-11-27 18:58:41
     */
    @Override
    public int insert(DatabaseConnectionDto databaseConnectiondto) {
        DatabaseConnection databaseConnection = DatabaseConnection.builder().build();
        BeanUtils.copyBeanProp(databaseConnection, databaseConnectiondto);
        return baseMapper.insert(databaseConnection);
    }

    /**
     * @param databaseConnectiondto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  修改数据并返回
     * &#064;date 2023-11-27 18:58:41
     */
    @Override
    public int update(DatabaseConnectionDto databaseConnectiondto) {
        DatabaseConnection databaseConnection = DatabaseConnection.builder().build();
        BeanUtils.copyBeanProp(databaseConnection, databaseConnectiondto);
        return baseMapper.updateById(databaseConnection);
    }

    /**
     * @param idList 主键
     * @return
     * @author ChenBaiYi
     * &#064;description //TODO    通过主键删除数据
     * &#064;date 2023-11-27 18:58:41
     */
    @Override
    public int deleteById(List<Long> idList) {
        return baseMapper.deleteBatchIds(idList);
    }
}
