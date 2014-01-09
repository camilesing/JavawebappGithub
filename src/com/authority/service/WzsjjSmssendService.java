package com.authority.service;


import java.util.List;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzsjjSmssend;

public interface WzsjjSmssendService {
    int countByExample(Criteria example);

    WzsjjSmssend selectByPrimaryKey(String id);

    List<WzsjjSmssend> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzsjjSmssend record);

    int updateByPrimaryKey(WzsjjSmssend record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzsjjSmssend record, Criteria example);

    int updateByExample(WzsjjSmssend record, Criteria example);

    int insert(WzsjjSmssend record);

    int insertSelective(WzsjjSmssend record);
}