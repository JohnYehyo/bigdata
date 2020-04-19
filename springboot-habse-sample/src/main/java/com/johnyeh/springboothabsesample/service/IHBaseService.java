package com.johnyeh.springboothabsesample.service;

import java.util.List;

/**
 * @author JohnYehyo
 * @date 2020-4-19
 */
public interface IHBaseService {

    List getRowKeyAndColumn(String tableName, String startRowkey, String stopRowkey, String column, String qualifier);

    List getListRowkeyData(String tableName, List<String> rowKeys, String familyColumn, String column);
}
