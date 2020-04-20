package com.johnyeh.springboothabsesample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


/**
 * @author JohnYehyo
 * @date 2020-4-20
 */
@RunWith(SpringRunner.class)
public class HbaseTest {

    @Test
    public void test() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","hbase1:2181,hbase2:2181,hbase3:2181");
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin connectionAdmin = connection.getAdmin();

        //创建表
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf("location"));

        //创建列簇
        HColumnDescriptor columnDescriptor = new HColumnDescriptor("locMsg");
        descriptor.addFamily(columnDescriptor);
        connectionAdmin.createTable(descriptor);
        connectionAdmin.close();
        connection.close();
    }
}
