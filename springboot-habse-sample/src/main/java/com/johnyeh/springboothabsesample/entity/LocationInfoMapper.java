package com.johnyeh.springboothabsesample.entity;

import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;

/**
 * @author JohnYehyo
 * @date 2020-4-22
 */
public class LocationInfoMapper implements RowMapper {

    @Override
    public Object mapRow(Result result, int i) throws Exception {
        return null;
    }
}
