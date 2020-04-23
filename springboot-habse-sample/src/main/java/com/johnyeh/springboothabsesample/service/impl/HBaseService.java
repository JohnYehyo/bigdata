package com.johnyeh.springboothabsesample.service.impl;

import com.johnyeh.springboothabsesample.entity.LocaitonInfo;
import com.johnyeh.springboothabsesample.service.IHBaseService;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JohnYehyo
 * @date 2020-4-19
 */
@Service
@Slf4j
public class HBaseService implements IHBaseService {

    @Autowired
    private HbaseTemplate hbaseTemplate;


    @Override
    public List getRowKeyAndColumn(String tableName, String startRowkey, String stopRowkey, String column, String qualifier) {
//        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
//        if (StringUtils.isNotBlank(column)) {
//            log.debug("{}", column);
//            filterList.addFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
//        }
//        if (StringUtils.isNotBlank(qualifier)) {
//            log.debug("{}", qualifier);
//            filterList.addFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(qualifier))));
//        }
//        Scan scan = new Scan();
//        if (filterList.getFilters().size() > 0) {
//            scan.setFilter(filterList);
//        }
//        scan.setStartRow(Bytes.toBytes(startRowkey));
//        scan.setStopRow(Bytes.toBytes(stopRowkey));
//
//        return hbaseTemplate.find(tableName, scan, (rowMapper, rowNum) -> rowMapper);
        return null;
    }

    @Override
    public List getListRowkeyData(String tableName, List<String> rowKeys, String familyColumn, String column) {
        return rowKeys.stream().map(rk -> {
            if (StringUtils.isNotBlank(familyColumn)) {
                if (StringUtils.isNotBlank(column)) {
                    return hbaseTemplate.get(tableName, rk, familyColumn, column, (rowMapper, rowNum) -> rowMapper);
                } else {
                    return hbaseTemplate.get(tableName, rk, familyColumn, (rowMapper, rowNum) -> rowMapper);
                }
            }
            return hbaseTemplate.get(tableName, rk, (result, i) -> {
                LocaitonInfo locaitonInfo = new LocaitonInfo();
                locaitonInfo.setName(new String(result.getValue("locMsg".getBytes(), "name".getBytes())));
                locaitonInfo.setPos(new String(result.getValue("locMsg".getBytes(), "pos".getBytes())));
                locaitonInfo.setTime(getFormatTime(new String(result.getValue("locMsg".getBytes(), "time".getBytes()))));
                return locaitonInfo;
            });
        }).collect(Collectors.toList());
    }

    private Date getFormatTime(String timestamp){
        Long time = Long.valueOf(timestamp);
        return new Date(time);
    }
}
