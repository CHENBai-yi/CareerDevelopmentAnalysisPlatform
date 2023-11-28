package com.example.chart.controller;

import cn.licoy.encryptbody.annotation.encrypt.DESEncryptBody;
import com.example.config.dynamicDb.annotation.DBUSE;
import com.example.db.dao.DatabaseConnectionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Author:XY
 * PACkAGE:com.example.chart.controller
 * Date:2023/11/24 15:17
 */
@Controller
@RequiredArgsConstructor
public class Index {
    private final DatabaseConnectionDao connectionDao;

    @DBUSE("test")
    @GetMapping("/2/{a}")
    @DESEncryptBody
    @ResponseBody
    public ResponseEntity<Object> i(@PathVariable("a") String s) {
        final List<String> strings = connectionDao.fromTestDb();
        final HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("msg", "查询至test数据库的user表");
        stringObjectHashMap.put("data", strings);
        stringObjectHashMap.put("ext", PACKAGE_CONST.AL);
        return ResponseEntity.ok(stringObjectHashMap);
    }
}
