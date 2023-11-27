package com.example.config.dynamicDb;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyDataSourceListTest {

    private MyDataSourceList myDataSourceListUnderTest;

    @Test
    void testDetermineCurrentLookupKey() {
        assertThat(myDataSourceListUnderTest.determineCurrentLookupKey()).isEqualTo("result");
    }
}
