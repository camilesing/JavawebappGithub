package com.authority.dao;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdSmssend;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YqgdSmssendMapper {
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
    int insert(YqgdSmssend record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(YqgdSmssend record);

    /**
     * 根据条件查询记录集
     */
    List<YqgdSmssend> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    YqgdSmssend selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("record") YqgdSmssend record, @Param("example") Criteria example);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("record") YqgdSmssend record, @Param("example") Criteria example);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(YqgdSmssend record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(YqgdSmssend record);
}