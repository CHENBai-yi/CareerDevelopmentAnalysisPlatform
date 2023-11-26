package com.example.chart.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.chart.dao.DetailsDao;
import com.example.chart.domain.dto.DetailsDto;
import com.example.chart.domain.mapper.DetailsToTableDto;
import com.example.chart.domain.pojo.Details;
import com.example.chart.domain.vo.ChartVo;
import com.example.chart.service.DetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Details)表服务实现类
 *
 * @author ChenBaiYi
 * @since 2023-11-23 15:52:10
 */
@Service("detailsService")
@RequiredArgsConstructor
public class DetailsServiceImpl extends ServiceImpl<DetailsDao, Details> implements DetailsService {

    private static long TOTAL = 0;
    @Value("#{${page.pageSize:4}}")
    private Long pageSize;
    @Value("#{${page.current:1}}")
    private Long current;

    /**
     * @param id 主键
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  通过ID查询单条数据
     * &#064;date 2023-11-23 15:52:10
     */
    @Override
    public Details selectDetailsById(String id) {
        LambdaQueryWrapper<Details> detailslam = new LambdaQueryWrapper<Details>();
        detailslam.eq(Details::getJobid, id);
        return baseMapper.selectOne(detailslam);
    }

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  分页查询数据
     * &#064;date 2023-11-23 15:52:10
     */
    @Override
    public List<Details> findDetailsSelectList(DetailsDto detailsdto) {
        Details details = Details.builder().build();
        BeanUtils.copyBeanProp(details, detailsdto);
        return baseMapper.selectDetailsList(details);
    }

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  新增数据
     * &#064;date 2023-11-23 15:52:10
     */
    @Override
    public int insert(DetailsDto detailsdto) {
        Details details = Details.builder().build();
        BeanUtils.copyBeanProp(details, detailsdto);
        return baseMapper.insert(details);
    }

    /**
     * @param detailsdto 实例对象
     * @return 实例对象
     * @author ChenBaiYi
     * &#064;description //TODO  修改数据并返回
     * &#064;date 2023-11-23 15:52:10
     */
    @Override
    public int update(DetailsDto detailsdto) {
        Details details = Details.builder().build();
        BeanUtils.copyBeanProp(details, detailsdto);
        return baseMapper.updateById(details);
    }

    /**
     * @param idList 主键
     * @return
     * @author ChenBaiYi
     * &#064;description //TODO    通过主键删除数据
     * &#064;date 2023-11-23 15:52:10
     */
    @Override
    public int deleteById(List<Long> idList) {
        return baseMapper.deleteBatchIds(idList);
    }

    @Transactional(readOnly = true)
    @Override
    public ChartVo getAll() {

        final ChartVo.Echarts2 echarts2 = getEcharts2();
        final List<ChartVo.TableDto> tableDto = getTableDto();
        final List<ChartVo.Echarts6> echarts6s = Optional.ofNullable(baseMapper.getEcharts6()).orElse(Collections.emptyList()).stream().filter(itewm -> StringUtils.isNotEmpty(itewm.getName())).sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).collect(Collectors.toList());
        final ChartVo.Echarts5 echarts51 = Optional.ofNullable(baseMapper.getEcharts5()).map(echarts5 -> {
            echarts5.setWork(echarts5.getWork().stream().filter(item -> StringUtils.isNotEmpty(item.getName())).collect(Collectors.toList()));
            return echarts5.setEducation(echarts6s);
        }).orElse(ChartVo.Echarts5.builder().build());
        final List<String> jobUpdateTime = baseMapper.getJobUpdateTime();
        final ChartVo.Echarts1 echarts1 = getEcharts1(jobUpdateTime);
        final ChartVo.Echarts4 echarts41 = Optional.ofNullable(baseMapper.getEcharts4()).map(echarts4s -> {
            final List<String> collect = echarts4s.stream().map(ChartVo.Echarts4::getName).collect(Collectors.toList());
            final List<Integer> collect1 = echarts4s.stream().map(ChartVo.Echarts4::getValue).collect(Collectors.toList());
            Collections.reverse(collect);
            Collections.reverse(collect1);
            return ChartVo.Echarts4.builder().build().setYL(collect).setData(collect1);
        }).orElse(ChartVo.Echarts4.builder().build());
        return ChartVo.builder().echarts2(echarts2).echarts1(echarts1).tableDto(tableDto).echarts6(echarts6s).echarts4(echarts41).echarts5(echarts51).build();
    }

    private ChartVo.Echarts1 getEcharts1(List<String> jobUpdateTime) {
        final HashMap<String, Map<String, List<Object>>> stringListMap = Optional.ofNullable(jobUpdateTime).map(time -> Optional.ofNullable(baseMapper.selectTop3(time)).map(details -> {
            final Set<String> collect = details.stream().map(item -> DateUtils.parseDateToStr("yyyy-MM", item.getUpdatedatetime())).collect(Collectors.toSet());
            return collect.stream().collect(Collectors.toMap(key -> key, val -> details.stream().filter(item -> DateUtils.parseDateToStr("yyyy-MM", item.getUpdatedatetime()).equals(val)).collect(Collectors.toList())));
        }).map(item -> {
            Map<String, List<Object>> data = new LinkedHashMap<>();
            Map<String, List<Object>> data1 = new LinkedHashMap<>();
            for (final String s : jobUpdateTime) {
                final List<Details> details = item.get(s);
                final Set<String> collect = details.stream().map(Details::getDegreestring).collect(Collectors.toSet());
                final Map<String, Long> degree = collect.stream().collect(Collectors.toMap(key -> key, val -> details.stream().filter(it -> val.equals(it.getDegreestring())).count()));
                final Set<String> collect1 = details.stream().map(Details::getWorkyearstring).collect(Collectors.toSet());
                final Map<String, Long> workYear = collect1.stream().collect(Collectors.toMap(key -> key, val -> details.stream().filter(it -> val.equals(it.getWorkyearstring())).count()));
                getCommon(data, degree, degree);
                getCommon(data1, workYear, workYear);
            }
            final HashMap<String, Map<String, List<Object>>> stringMapHashMap = new HashMap<>();
            stringMapHashMap.put("degree", data);
            stringMapHashMap.put("workYear", data1);
            return stringMapHashMap;
        })).get().get();
        return ChartVo.Echarts1.builder().months(jobUpdateTime).months(jobUpdateTime).data(stringListMap).build();
    }

    private void getCommon(final Map<String, List<Object>> data, final Map<String, Long> degree, final Map<String, Long> workYear) {
        for (final String s1 : workYear.keySet()) {
            List<Object> list = null;
            if (data.get(s1) == null) {
                list = new ArrayList<>();
            } else {
                list = data.get(s1);
            }
            final Long e = degree.get(s1);
            if (e == null) {
                continue;
            }
            list.add(e);
            data.put(s1, list);
        }
    }

    private ChartVo.Echarts2 getEcharts2() {
        final Map<String, Map<String, String>> stringJSONObjectMap = baseMapper.selectEchart2();
        Map<String, List<Object>> data = new HashMap<>();
        final Set<String> keySet = stringJSONObjectMap.keySet();
        for (final String s : keySet) {
            final Map<String, String> stringStringMap = stringJSONObjectMap.get(s);
            final String detailsList = stringStringMap.get("details_list");
            Optional.ofNullable((JSONArray) JSONObject.parse(detailsList)).ifPresent(item -> {
                final List<JSONObject> jsonObjects = item.stream().map(o -> (JSONObject) o).collect(Collectors.toList());
                final Set<String> companyTypeString = jsonObjects.stream().map(i1 -> i1.getString("companyTypeString")).collect(Collectors.toSet());
                for (final String s1 : companyTypeString) {
                    final long companyTypeString1 = jsonObjects.stream().filter(i2 -> s1.equals(i2.getString("companyTypeString"))).count();
                    List<Object> list = null;
                    if (data.get(s1) == null) {
                        list = new LinkedList<>();
                    } else {
                        list = data.get(s1);
                    }
                    list.add(companyTypeString1);
                    data.put(s1, list);
                }
            });
        }
        return ChartVo.Echarts2.builder().data(data).months(Arrays.asList(keySet.toArray(new String[]{}))).build();
    }

    private List<ChartVo.TableDto> getTableDto() {
        final PageDTO<Details> detailsDtoPageDTO = new PageDTO<>(current, pageSize, true);
        return Optional.ofNullable(baseMapper.selectPage(detailsDtoPageDTO, new QueryWrapper<>())).map(item -> {
            if (item.getTotal() != TOTAL) {
                TOTAL = item.getTotal();
            }
            if (item.hasNext()) {
                current = item.getCurrent() + 1;
            } else {
                current = 1L;
            }

            return DetailsToTableDto.DETAILS_TO_TABLE_DTO.getTableDtos(item.getRecords().stream().map(i -> {
                if (StringUtils.isEmpty(i.getHrlabels())) {
                    i.setHrlabels("未知");
                } else {
                    i.setHrlabels(JSONObject.parseObject(i.getHrlabels(), List.class).stream().collect(Collectors.joining(", ")) + "");
                }
                return i;
            }).collect(Collectors.toList()));
        }).orElse(Collections.emptyList());
    }

}
