package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdSmsreturn;
import java.util.List;

public interface WzgdSmsreturnService {
    int countByExample(Criteria example);

    WzgdSmsreturn selectByPrimaryKey(String id);

    List<WzgdSmsreturn> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzgdSmsreturn record);

    int updateByPrimaryKey(WzgdSmsreturn record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzgdSmsreturn record, Criteria example);

    int updateByExample(WzgdSmsreturn record, Criteria example);

    int insert(WzgdSmsreturn record);

    int insertSelective(WzgdSmsreturn record);
}