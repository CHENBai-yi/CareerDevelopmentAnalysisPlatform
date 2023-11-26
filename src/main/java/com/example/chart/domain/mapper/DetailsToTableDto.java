package com.example.chart.domain.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.chart.domain.pojo.Details;
import com.example.chart.domain.vo.ChartVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:XY
 * PACkAGE:com.example.chart.domain.mapper
 * Date:2023/11/25 19:43
 */
@Mapper
public interface DetailsToTableDto {
    DetailsToTableDto DETAILS_TO_TABLE_DTO = Mappers.getMapper(DetailsToTableDto.class);

    @Mappings({
            @Mapping(target = "hrisonline", expression = "java(details.getHrisonline()==1?\"在线\":\"离线\")")
    })
    ChartVo.TableDto getTableDto(Details details);

    List<ChartVo.TableDto> getTableDtos(List<Details> details);
}
