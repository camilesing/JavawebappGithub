package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdSmssend;
import java.util.List;

public interface WzgdSmssendService {
    int countByExample(Criteria example);

    WzgdSmssend selectByPrimaryKey(String id);

    List<WzgdSmssend> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzgdSmssend record);

    int updateByPrimaryKey(WzgdSmssend record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzgdSmssend record, Criteria example);

    int updateByExample(WzgdSmssend record, Criteria example);

    int insert(WzgdSmssend record);

    int insertSelective(WzgdSmssend record);
}