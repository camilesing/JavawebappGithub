package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdResponsedata;
import java.util.List;

public interface YqgdResponsedataService {
    int countByExample(Criteria example);

    YqgdResponsedata selectByPrimaryKey(String id);

    List<YqgdResponsedata> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdResponsedata record);

    int updateByPrimaryKey(YqgdResponsedata record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdResponsedata record, Criteria example);

    int updateByExample(YqgdResponsedata record, Criteria example);

    int insert(YqgdResponsedata record);

    int insertSelective(YqgdResponsedata record);
}