package com.example.chart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.chart.domain.dto.DetailsDto;
import com.example.chart.domain.pojo.Details;
import com.example.chart.domain.vo.ChartVo;

import java.util.List;

/**
 * (Details)表服务接口
 *
 * @author ChenBaiYi
 * @since 2023-11-23 15:52:10
 */
public interface DetailsService extends IService<Details> {

    /**
     * @param id 主键
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description  通过ID查询单条数据
     * &#064;date 2023-11-23 15:52:10
     */
    Details selectDetailsById(final String id);

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description 分页查询数据
     * &#064;date 2023-11-23 15:52:10
     */
    List<Details> findDetailsSelectList(final DetailsDto detailsdto);

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description  新增数据
     * &#064;date 2023-11-23 15:52:10
     */
    int insert(final DetailsDto detailsdto);

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description 修改数据并返回
     * &#064;date 2023-11-23 15:52:10
     */
    int update(final DetailsDto detailsdto);

    /**
     * @param ids 主键
     * @return
     * @author ChenBaiYi
     * &#064;description  通过主键删除数据
     * &#064;date 2023-11-23 15:52:10
     */
    int deleteById(final List<Long> ids);

    ChartVo getAll();

}
