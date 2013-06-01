package com.mybatis_generator.dao;

import com.mybatis_generator.model.BaseUsers;
import com.mybatis_generator.model.BaseUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseUsersMapper {
    int countByExample(BaseUsersExample example);

    int deleteByExample(BaseUsersExample example);

    int deleteByPrimaryKey(String userId);

    int insert(BaseUsers record);

    int insertSelective(BaseUsers record);

    List<BaseUsers> selectByExample(BaseUsersExample example);

    BaseUsers selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") BaseUsers record, @Param("example") BaseUsersExample example);

    int updateByExample(@Param("record") BaseUsers record, @Param("example") BaseUsersExample example);

    int updateByPrimaryKeySelective(BaseUsers record);

    int updateByPrimaryKey(BaseUsers record);
}