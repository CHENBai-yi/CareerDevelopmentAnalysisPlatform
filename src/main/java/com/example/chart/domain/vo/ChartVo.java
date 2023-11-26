package com.example.chart.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * Author:XY
 * PACkAGE:com.example.chart.domain.vo
 * Date:2023/11/24 12:30
 *
 * @author BaiYiChen
 */
@Data
@Builder
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class ChartVo {
    @JsonProperty("c4")
    private Echarts4 echarts4;
    @JsonProperty("c5")
    private Echarts5 echarts5;
    @JsonProperty("c6")
    private List<Echarts6> echarts6;
    @JsonProperty("c1")
    private Echarts1 echarts1;
    @JsonProperty("c2")
    private Echarts2 echarts2;
    @JsonProperty("tableData")
    private List<TableDto> tableDto;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class Echarts4 {
        @JsonIgnore
        private String name;
        @JsonIgnore
        private Integer value;
        private List<String> yL;
        private List<Integer> yR;
        private List<Integer> data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class Echarts5 {
        private String name;
        private Long value;
        private List<Echarts6> education;
        private List<Echarts5> work;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class Echarts6 {
        private String name;
        private Long value;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class Echarts1 {
        private List<String> months;
        @JsonProperty("data")
        private Map<String, Map<String, List<Object>>> data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class Echarts2 {
        private List<String> months;
        @JsonProperty("data")
        private Map<String, List<Object>> data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
    public static class TableDto {
        private String jobname;
        private String jobareastring;
        private String providesalarystring;
        private String workyearstring;
        private String degreestring;
        private String companyname;
        private String companytypestring;
        private String companysizestring;
        private String hrname;
        private String hrisonline;
        private String hrlabels;
        private String jobhref;
    }

}
