package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdRequestdata;
import java.util.List;

public interface YqgdRequestdataService {
    int countByExample(Criteria example);

    YqgdRequestdata selectByPrimaryKey(String id);

    List<YqgdRequestdata> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdRequestdata record);

    int updateByPrimaryKey(YqgdRequestdata record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdRequestdata record, Criteria example);

    int updateByExample(YqgdRequestdata record, Criteria example);

    int insert(YqgdRequestdata record);

    int insertSelective(YqgdRequestdata record);
}