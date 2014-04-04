package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdBankcode;
import java.util.List;

public interface YqgdBankcodeService {
    int countByExample(Criteria example);

    YqgdBankcode selectByPrimaryKey(String id);

    List<YqgdBankcode> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdBankcode record);

    int updateByPrimaryKey(YqgdBankcode record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdBankcode record, Criteria example);

    int updateByExample(YqgdBankcode record, Criteria example);

    int insert(YqgdBankcode record);

    int insertSelective(YqgdBankcode record);
}