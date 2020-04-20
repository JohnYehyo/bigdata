package com.johnyeh.springboothabsesample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JohnYehyo
 * @date 2020-4-20
 */
@RunWith(SpringRunner.class)
public class HbaseTest {

    private static final TableName TABLENAME = TableName.valueOf("location");
    private static final String COLUMNNAME = "locMsg";

    private Connection connection = null;

    @Before
    public void getConnection() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hbase1:2181,hbase2:2181,hbase3:2181");
        connection =  ConnectionFactory.createConnection(configuration);
    }



    @Test
    public void createTable() throws IOException {

        Admin connectionAdmin = connection.getAdmin();
        //创建表
        HTableDescriptor tableDescriptor = new HTableDescriptor(TABLENAME);
        //创建列簇
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(COLUMNNAME);
        tableDescriptor.addFamily(columnDescriptor);

        connectionAdmin.createTable(tableDescriptor);
        connectionAdmin.close();

    }

    @Test
    public void addData() throws IOException {

        Table table = connection.getTable(TABLENAME);
        List<Put> list = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("000000000000");
        Put put = null;
        for (int i = 0; i < 1000; i++) {
            put = new Put(decimalFormat.format(i).getBytes());
            put.addColumn(COLUMNNAME.getBytes(), "name".getBytes(), ("张三"+i).getBytes());
            put.addColumn(COLUMNNAME.getBytes(), "time".getBytes(), String.valueOf(System.currentTimeMillis()).getBytes());
            put.addColumn(COLUMNNAME.getBytes(), "pos".getBytes(), ("测试地点"+i*i).getBytes());
            list.add(put);

            if((i+1) % 200 == 0){
                table.put(list);
                list.clear();
            }
        }
//        table.put(list);
        table.close();
    }


    @After
    public void close(){
        if(null != connection){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
