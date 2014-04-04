package com.authority.service.impl;

import com.authority.dao.YqgdResponsedataMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdResponsedata;
import com.authority.service.YqgdResponsedataService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YqgdResponsedataServiceImpl implements YqgdResponsedataService {
    @Autowired
    private YqgdResponsedataMapper yqgdResponsedataMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdResponsedataServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdResponsedataMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdResponsedata selectByPrimaryKey(String id) {
        return this.yqgdResponsedataMapper.selectByPrimaryKey(id);
    }

    public List<YqgdResponsedata> selectByExample(Criteria example) {
        return this.yqgdResponsedataMapper.selectByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.yqgdResponsedataMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(YqgdResponsedata record) {
        return this.yqgdResponsedataMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(YqgdResponsedata record) {
        return this.yqgdResponsedataMapper.updateByPrimaryKey(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.yqgdResponsedataMapper.deleteByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(YqgdResponsedata record, Criteria example) {
        return this.yqgdResponsedataMapper.updateByExampleSelective(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(YqgdResponsedata record, Criteria example) {
        return this.yqgdResponsedataMapper.updateByExample(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(YqgdResponsedata record) {
        return this.yqgdResponsedataMapper.insert(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(YqgdResponsedata record) {
        return this.yqgdResponsedataMapper.insertSelective(record);
    }
}