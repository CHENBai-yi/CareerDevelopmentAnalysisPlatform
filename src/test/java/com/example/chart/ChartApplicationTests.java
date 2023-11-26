package com.example.chart;

import com.example.chart.dao.DetailsDao;
import com.example.chart.domain.vo.ChartVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChartApplicationTests {
    @Autowired
    private DetailsDao detailsDao;

    @Test
    void contextLoads() {
        System.out.println(detailsDao.selectEchart1());
    }

}
