
package com.example.demo.service;

import java.util.List;


/**
 * @author  Pre_fantasy
 * @create  2018-06-28 20:24
 * @desc    创建Redis常用方法
 **/

public interface RedisService {


/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:26
     *  @param  {String  key, String value}
     *  @return boolean
     *  @desc   设置给定key的值，如果key已经储存其他值，set就覆盖旧值且无视类型
     */

    boolean set(String key, String value);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:30
     *  @param  {String key}
     *  @return String
     *  @desc   获取指定key的值，如果key不存在，返回null。如果key储存的值不是字符串，返回一个错误。
     */

    String get(String key);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:38
     *  @param  {String key, long expire}
     *  @return boolean
     *  @desc   设置key的过期时间。key过期后不可再用
     */

    boolean expire(String key, long expire);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:40
     *  @param  {String key, List<T> list}
     *  @return boolean
     *  @desc   存储集合
     */

    <T> boolean setList(String key, List<T> list);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:30
     *  @param  {String key, Class<T> clz
     *  @return List<T>
     *  @desc   同过key获取List<T>
     */

    <T> List<T> getList(String key, Class<T> clz);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:34
     *  @param  {String key, Object obj}
     *  @return long
     *  @desc   将一个或多个值掺入到 列表头部， 如果key不存在，一个空列表会被创建并执行lpush操作。
     *          当key存在但不是列表类，返回一个错误
     */

    long lpush(String key, Object obj);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:39
     *  @param  {String key, Object obj}
     *  @return long
     *  @desc   将一个或多个值插入到类表的尾部（最右边）
     *          如果列表不存在，一个空列表会被创建出来并且执行rpush，当列表存在但不是列表类型时， 返回一个错误。
     */

    long rpush(String key, Object obj);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:52
     *  @param  {String key}
     *  @return String
     *  @desc   移除并返回列表的第一个元素
     */

    String lpop(String key);


/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:53
     *  @param  {final String key}
     *  @return long
     *  @desc   删除已存在的键。 不存在的key会被忽略。
     */

    long del(final String key);


}










