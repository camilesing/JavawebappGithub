package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdSmssend;
import java.util.List;

public interface YqgdSmssendService {
    int countByExample(Criteria example);

    YqgdSmssend selectByPrimaryKey(String id);

    List<YqgdSmssend> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdSmssend record);

    int updateByPrimaryKey(YqgdSmssend record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdSmssend record, Criteria example);

    int updateByExample(YqgdSmssend record, Criteria example);

    int insert(YqgdSmssend record);

    int insertSelective(YqgdSmssend record);
}