package com.johnyeh.springboothabsesample.service.impl;

//import com.github.xiangwangjianghu.template.HBaseTemplate;
import com.johnyeh.springboothabsesample.service.IHBaseService;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.apache.hadoop.hbase.client.Scan;
//import org.apache.hadoop.hbase.filter.*;
//import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        if (StringUtils.isNotBlank(column)) {
            log.debug("{}", column);
            filterList.addFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
        }
        if (StringUtils.isNotBlank(qualifier)) {
            log.debug("{}", qualifier);
            filterList.addFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(qualifier))));
        }
        Scan scan = new Scan();
        if (filterList.getFilters().size() > 0) {
            scan.setFilter(filterList);
        }
        scan.setStartRow(Bytes.toBytes(startRowkey));
        scan.setStopRow(Bytes.toBytes(stopRowkey));

        return hbaseTemplate.find(tableName, scan, (rowMapper, rowNum) -> rowMapper);
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
            return hbaseTemplate.get(tableName, rk, (rowMapper, rowNum) -> rowMapper);
        }).collect(Collectors.toList());
    }
}
