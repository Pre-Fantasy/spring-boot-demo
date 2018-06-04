package com.example.demo.core.universal;

import com.example.demo.core.ret.ServiceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**

 * @author  Pre_fantasy

 * @create  2018-06-03 19:46

 * @desc    基于通用MyBatis Mapper插件的Service接口的实现

 **/
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    /*当前泛型真实类型的Class*/
    private Class<T> modelClass;

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 20:03
     *  @param
     *  @return
     *  @desc   当前泛型真实类型的Class,通过构造器赋值（以保证在创建对象时就拥有值），
     */
    @SuppressWarnings("unchecked")
    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }


    @Override
    public Integer insert(T model) {
        return mapper.insertSelective(model);
    }

    @Override
    public Integer deleteById(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public Integer update(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T selectById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:37
     *  @param  fieldName (String), value(Object)
     *  @return T
     *  @desc   通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     */
    public T selectBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/3 19:39
     *  @param  fieldName (String), value(Object)
     *  @return List</T>
     *  @desc   通过Model中某个成员变量名称（非数据表中column的名称）查找
     */
    @Override
    public List<T> selectListBy(String fieldName, Object value)  {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.select(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> selectListByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> selectByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> select(T record){
        return mapper.select(record);
    }

    @Override
    public T selectOne(T recoed){
        return mapper.selectOne(recoed);
    }

}
