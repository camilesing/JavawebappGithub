package com.authority.service;


import java.util.List;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzsjjSmsreturn;

public interface WzsjjSmsreturnService {
    int countByExample(Criteria example);

    WzsjjSmsreturn selectByPrimaryKey(String id);

    List<WzsjjSmsreturn> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzsjjSmsreturn record);

    int updateByPrimaryKey(WzsjjSmsreturn record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzsjjSmsreturn record, Criteria example);

    int updateByExample(WzsjjSmsreturn record, Criteria example);

    int insert(WzsjjSmsreturn record);

    int insertSelective(WzsjjSmsreturn record);
}