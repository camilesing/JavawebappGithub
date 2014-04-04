package com.authority.dao;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdResponsedata;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YqgdResponsedataMapper {
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
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(YqgdResponsedata record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(YqgdResponsedata record);

    /**
     * 根据条件查询记录集
     */
    List<YqgdResponsedata> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    YqgdResponsedata selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("record") YqgdResponsedata record, @Param("example") Criteria example);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("record") YqgdResponsedata record, @Param("example") Criteria example);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(YqgdResponsedata record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(YqgdResponsedata record);
}