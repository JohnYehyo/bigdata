package com.johnyeh.springboothabsesample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JohnYehyo
 * @date 2020-4-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocaitonInfo {

    private String name;
    private String pos;
    private Date time;
}
