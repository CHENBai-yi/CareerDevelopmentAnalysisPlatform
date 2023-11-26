package com.example.chart.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.chart.domain.vo.ChartVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import com.example.chart.domain.pojo.Details;
import org.apache.ibatis.annotations.Param;

/**
 * (Details)表数据库访问层
 *
 * @author ChenBaiYi
 * @since 2023-11-23 15:52:08
 */
@Mapper
public interface DetailsDao extends BaseMapper<Details> {
    /**
     * @param details 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * @description 分页查询数据
     * @date 2023-11-23 15:52:08
     */
    List<Details> selectDetailsList(Details details);

    List<ChartVo.Echarts6> getEcharts6();

    List<ChartVo.Echarts4> getEcharts4();

    ChartVo.Echarts5 getEcharts5();

    List<String> getJobUpdateTime();

    List<Details> selectTop3(List<String> timeList);

    @MapKey("month_year")
    Map<String, Map<String, String>> selectEchart2();

    List<ChartVo.TableDto> selectAllTableDto();
}


