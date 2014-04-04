package com.authority.service;

import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdRequestsms;
import java.util.List;

public interface YqgdRequestsmsService {
    int countByExample(Criteria example);

    YqgdRequestsms selectByPrimaryKey(String id);

    List<YqgdRequestsms> selectByExample(Criteria example);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YqgdRequestsms record);

    int updateByPrimaryKey(YqgdRequestsms record);

    int deleteByExample(Criteria example);

    int updateByExampleSelective(YqgdRequestsms record, Criteria example);

    int updateByExample(YqgdRequestsms record, Criteria example);

    int insert(YqgdRequestsms record);

    int insertSelective(YqgdRequestsms record);
}