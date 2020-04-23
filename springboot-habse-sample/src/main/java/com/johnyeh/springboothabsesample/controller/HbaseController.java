package com.johnyeh.springboothabsesample.controller;

import com.johnyeh.springboothabsesample.service.IHBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JohnYehyo
 * @date 2020-4-19
 */
@RestController
@RequestMapping(value = "hbase")
public class HbaseController {

    @Autowired
    private IHBaseService hbaseService;

    @GetMapping
    public Map test(){
        Map map = new HashMap();
        map.put("hey", "Hbase");
        return map;
    }

    @GetMapping(value = "list")
    public List list(){
        String tableName = "location";
        List<String> rowKeys = new ArrayList<>();
        rowKeys.add("000000000001");
        rowKeys.add("000000000002");
        String familyColumn = null;
        String column = "locMsg";
        List list = hbaseService.getListRowkeyData(tableName, rowKeys, familyColumn, column);
        return list;
    }
}
