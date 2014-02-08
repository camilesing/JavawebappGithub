package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdShoulywnr;
import java.util.List;

public interface WzgdShoulywnrService {
    int countByExample(Criteria example);

    WzgdShoulywnr selectByPrimaryKey(String id);

    List<WzgdShoulywnr> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WzgdShoulywnr record);

    int updateByPrimaryKey(WzgdShoulywnr record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(WzgdShoulywnr record, Criteria example);

    int updateByExample(WzgdShoulywnr record, Criteria example);

    int insert(WzgdShoulywnr record);

    int insertSelective(WzgdShoulywnr record);
}