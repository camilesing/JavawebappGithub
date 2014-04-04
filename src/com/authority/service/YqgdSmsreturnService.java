package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdSmsreturn;
import java.util.List;

public interface YqgdSmsreturnService {
    int countByExample(Criteria example);

    YqgdSmsreturn selectByPrimaryKey(String id);

    List<YqgdSmsreturn> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdSmsreturn record);

    int updateByPrimaryKey(YqgdSmsreturn record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdSmsreturn record, Criteria example);

    int updateByExample(YqgdSmsreturn record, Criteria example);

    int insert(YqgdSmsreturn record);

    int insertSelective(YqgdSmsreturn record);
}