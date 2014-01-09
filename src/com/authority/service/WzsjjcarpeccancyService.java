package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.Wzsjjcarpeccancy;
import java.util.List;

public interface WzsjjcarpeccancyService {
    int countByExample(Criteria example);

    Wzsjjcarpeccancy selectByPrimaryKey(String id);

    List<Wzsjjcarpeccancy> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Wzsjjcarpeccancy record);

    int updateByPrimaryKey(Wzsjjcarpeccancy record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(Wzsjjcarpeccancy record, Criteria example);

    int updateByExample(Wzsjjcarpeccancy record, Criteria example);

    int insert(Wzsjjcarpeccancy record);

    int insertSelective(Wzsjjcarpeccancy record);
}