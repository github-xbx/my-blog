package com.xingbingxuan.blog.database;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * redis k-v 实体
 * @author : xbx
 * @date : 2022/8/28 15:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RedisEntity {


    private String key;
    private Object value;
}
