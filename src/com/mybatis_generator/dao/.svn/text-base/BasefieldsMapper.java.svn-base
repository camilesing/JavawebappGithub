package com.mybatis_generator.dao;

import com.mybatis_generator.pojo.Basefields;
import com.mybatis_generator.pojo.Criteria;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface BasefieldsMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String fieldId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Basefields record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Basefields record);

    /**
     * 根据条件查询记录集
     */
    List<Basefields> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Basefields selectByPrimaryKey(String fieldId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("record") Basefields record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("record") Basefields record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Basefields record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Basefields record);
}