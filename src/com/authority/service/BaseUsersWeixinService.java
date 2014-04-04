package com.authority.service;

import com.authority.pojo.BaseUsersWeixin;
import com.authority.pojo.Criteria;
import java.util.List;

public interface BaseUsersWeixinService {
    int countByExample(Criteria example);

    BaseUsersWeixin selectByPrimaryKey(String id);

    List<BaseUsersWeixin> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BaseUsersWeixin record);

    int updateByPrimaryKey(BaseUsersWeixin record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(BaseUsersWeixin record, Criteria example);

    int updateByExample(BaseUsersWeixin record, Criteria example);

    int insert(BaseUsersWeixin record);

    int insertSelective(BaseUsersWeixin record);
}