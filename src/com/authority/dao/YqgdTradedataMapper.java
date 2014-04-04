package com.authority.dao;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdTradedata;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YqgdTradedataMapper {
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
    int insert(YqgdTradedata record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(YqgdTradedata record);

    /**
     * 根据条件查询记录集
     */
    List<YqgdTradedata> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    YqgdTradedata selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("record") YqgdTradedata record, @Param("example") Criteria example);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("record") YqgdTradedata record, @Param("example") Criteria example);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(YqgdTradedata record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(YqgdTradedata record);
}