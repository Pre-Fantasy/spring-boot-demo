package com.example.demo.core.universal;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author  dell
 * @create  2018-06-03 19:18
 * @desc    定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 **/
public interface Mapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T>, SelectByPrimaryKeyMapper<T> {
}
