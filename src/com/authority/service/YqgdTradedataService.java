package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdTradedata;
import java.util.List;

public interface YqgdTradedataService {
    int countByExample(Criteria example);

    YqgdTradedata selectByPrimaryKey(String id);

    List<YqgdTradedata> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdTradedata record);

    int updateByPrimaryKey(YqgdTradedata record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdTradedata record, Criteria example);

    int updateByExample(YqgdTradedata record, Criteria example);

    int insert(YqgdTradedata record);

    int insertSelective(YqgdTradedata record);
}