package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.Wzsjjcarregister;
import java.util.List;

public interface WzsjjcarregisterService {
    int countByExample(Criteria example);

    Wzsjjcarregister selectByPrimaryKey(String id);

    List<Wzsjjcarregister> selectByExample(Criteria example);


    String updateByPrimaryKeySelective(Wzsjjcarregister record);

    int updateByPrimaryKey(Wzsjjcarregister record);

    String deleteByExample(Criteria example);

    int updateByExampleSelective(Wzsjjcarregister record, Criteria example);

    int updateByExample(Wzsjjcarregister record, Criteria example);

    int insert(Wzsjjcarregister record);
    
    String saveCar(Criteria example);
    
}