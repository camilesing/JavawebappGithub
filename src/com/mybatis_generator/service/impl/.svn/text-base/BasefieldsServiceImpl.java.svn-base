package com.mybatis_generator.service.impl;

import com.mybatis_generator.dao.BasefieldsMapper;
import com.mybatis_generator.pojo.Basefields;
import com.mybatis_generator.pojo.Criteria;
import com.mybatis_generator.service.BasefieldsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasefieldsServiceImpl implements BasefieldsService {
    @Autowired
    private BasefieldsMapper basefieldsMapper;

    private static final Logger logger = LoggerFactory.getLogger(BasefieldsServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.basefieldsMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Basefields selectByPrimaryKey(String fieldId) {
        return this.basefieldsMapper.selectByPrimaryKey(fieldId);
    }

    public List<Basefields> selectByExample(Criteria example) {
        return this.basefieldsMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String fieldId) {
        return this.basefieldsMapper.deleteByPrimaryKey(fieldId);
    }

    public int updateByPrimaryKeySelective(Basefields record) {
        return this.basefieldsMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Basefields record) {
        return this.basefieldsMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.basefieldsMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Basefields record, Criteria example) {
        return this.basefieldsMapper.updateByExampleSelective(record, example.getCondition());
    }

    public int updateByExample(Basefields record, Criteria example) {
        return this.basefieldsMapper.updateByExample(record, example.getCondition());
    }

    public int insert(Basefields record) {
        return this.basefieldsMapper.insert(record);
    }

    public int insertSelective(Basefields record) {
        return this.basefieldsMapper.insertSelective(record);
    }
}