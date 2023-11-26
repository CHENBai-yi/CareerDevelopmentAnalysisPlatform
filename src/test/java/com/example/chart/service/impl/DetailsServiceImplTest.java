package com.example.chart.service.impl;

import com.example.chart.dao.DetailsDao;
import com.example.chart.domain.dto.DetailsDto;
import com.example.chart.domain.pojo.Details;
import com.example.chart.domain.vo.ChartVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DetailsServiceImplTest {

    @Mock
    private DetailsDao mockDetailsDao;

    private DetailsServiceImpl detailsServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        detailsServiceImplUnderTest = new DetailsServiceImpl(mockDetailsDao);
    }

    @Test
    void testSelectDetailsById() {
        // Setup
        final Details expectedResult = Details.builder()
                .jobid("jobid")
                .degreestring("degreestring")
                .updatedatetime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build();

        // Run the test
        final Details result = detailsServiceImplUnderTest.selectDetailsById("id");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindDetailsSelectList() {
        // Setup
        final DetailsDto detailsdto = DetailsDto.builder().build();
        final List<Details> expectedResult = Arrays.asList(Details.builder()
                .jobid("jobid")
                .degreestring("degreestring")
                .updatedatetime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())
                .build());

        // Run the test
        final List<Details> result = detailsServiceImplUnderTest.findDetailsSelectList(detailsdto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testInsert() {
        // Setup
        final DetailsDto detailsdto = DetailsDto.builder().build();

        // Run the test
        final int result = detailsServiceImplUnderTest.insert(detailsdto);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testUpdate() {
        // Setup
        final DetailsDto detailsdto = DetailsDto.builder().build();

        // Run the test
        final int result = detailsServiceImplUnderTest.update(detailsdto);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testDeleteById() {
        // Setup
        // Run the test
        final int result = detailsServiceImplUnderTest.deleteById(Arrays.asList(0L));

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetAll() {
        // Setup
        final ChartVo expectedResult = ChartVo.builder()
                .echarts4(ChartVo.Echarts4.builder()
                        .name("name")
                        .value(0)
                        .yL(Arrays.asList("value"))
                        .data(Arrays.asList(0))
                        .build())
                .echarts5(ChartVo.Echarts5.builder()
                        .name("name")
                        .education(Arrays.asList(ChartVo.Echarts6.builder()
                                .name("name")
                                .value(0L)
                                .build()))
                        .work(Arrays.asList())
                        .build())
                .echarts6(Arrays.asList(ChartVo.Echarts6.builder()
                        .name("name")
                        .value(0L)
                        .build()))
                .build();

        // Run the test
        final ChartVo result = detailsServiceImplUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
