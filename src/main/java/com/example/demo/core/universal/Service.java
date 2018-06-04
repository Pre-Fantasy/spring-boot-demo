package com.example.demo.core.universal;


import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**

 * @author  Pre_fantas

 * @create  2018-06-03 19:20

 * @desc    Service基础接口，其他Service请继承改接口

 **/
public interface Service<T> {

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:22
     *  @param  model T
     *  @return Integer
     *  @desc   持久化
     */
    Integer insert(T model);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:32
     *  @param  id      String
     *  @return Integer
     *  @desc   更具id删除数据
     */
    Integer deleteById(String id);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:34
     *  @param  ids     String
     *  @return Integer
     *  @desc   批量删除 ids-> "1,2,3......"
     */
    Integer deleteByIds(String ids);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:36
     *  @param  model      T
     *  @return Integer
     *  @desc   根据id修改数据
     */
    Integer update(T model);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:37
     *  @param  fieldName (String), value(Object)
     *  @return T
     *  @desc   通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     */
    T selectBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:39
     *  @param  fieldName (String), value(Object)
     *  @return List</T>
     *  @desc   通过Model中某个成员变量名称（非数据表中column的名称）查找
     */
    List<T> selectListBy(String fieldName, Object value);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:40
     *  @param  ids     String
     *  @return List<T>
     *  @desc   通过多个ID查找 ids-> "1,2,3......"
     */
    List<T> selectListByIds(String ids);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:42
     *  @param  condition   Condition
     *  @return List<T>
     *  @desc   根据条件查找
     */
    List<T> selectByCondition(Condition condition);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:43
     *  @param  ""    null
     *  @return List<T>
     *  @desc   获取所有
     */
    List<T> selectAll();

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:44
     *  @param  record      T
     *  @return List<T>
     *  @desc   根据实体中的属性值进行查询，查询条件使用等号
     */
    List<T> select(T record);

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:45
     *  @param  record      T
     *  @return T
     *  @desc   根据实体中的属性值进行查询，查询条件使用等号
     */
    T selectOne(T record);

    T selectById(String id);
}
