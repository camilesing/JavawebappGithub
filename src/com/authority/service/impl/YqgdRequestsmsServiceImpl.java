package com.authority.service.impl;

import com.authority.dao.YqgdRequestsmsMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdRequestsms;
import com.authority.service.YqgdRequestsmsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YqgdRequestsmsServiceImpl implements YqgdRequestsmsService {
    @Autowired
    private YqgdRequestsmsMapper yqgdRequestsmsMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdRequestsmsServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdRequestsmsMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdRequestsms selectByPrimaryKey(String id) {
        return this.yqgdRequestsmsMapper.selectByPrimaryKey(id);
    }

    public List<YqgdRequestsms> selectByExample(Criteria example) {
        return this.yqgdRequestsmsMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.yqgdRequestsmsMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(YqgdRequestsms record) {
        return this.yqgdRequestsmsMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(YqgdRequestsms record) {
        return this.yqgdRequestsmsMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.yqgdRequestsmsMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(YqgdRequestsms record, Criteria example) {
        return this.yqgdRequestsmsMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(YqgdRequestsms record, Criteria example) {
        return this.yqgdRequestsmsMapper.updateByExample(record, example);
    }

    public int insert(YqgdRequestsms record) {
        return this.yqgdRequestsmsMapper.insert(record);
    }

    public int insertSelective(YqgdRequestsms record) {
        return this.yqgdRequestsmsMapper.insertSelective(record);
    }
}