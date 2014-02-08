package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdKehjbxx;
import java.util.List;

public interface WzgdKehjbxxService {
    int countByExample(Criteria example);

    WzgdKehjbxx selectByPrimaryKey(String id);

    List<WzgdKehjbxx> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzgdKehjbxx record);

    int updateByPrimaryKey(WzgdKehjbxx record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzgdKehjbxx record, Criteria example);

    int updateByExample(WzgdKehjbxx record, Criteria example);

    int insert(WzgdKehjbxx record);

    int insertSelective(WzgdKehjbxx record);
}