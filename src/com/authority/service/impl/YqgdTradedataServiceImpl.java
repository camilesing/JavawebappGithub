package com.authority.service.impl;

import com.authority.dao.YqgdTradedataMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdTradedata;
import com.authority.service.YqgdTradedataService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YqgdTradedataServiceImpl implements YqgdTradedataService {
    @Autowired
    private YqgdTradedataMapper yqgdTradedataMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdTradedataServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdTradedataMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdTradedata selectByPrimaryKey(String id) {
        return this.yqgdTradedataMapper.selectByPrimaryKey(id);
    }

    public List<YqgdTradedata> selectByExample(Criteria example) {
        return this.yqgdTradedataMapper.selectByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.yqgdTradedataMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(YqgdTradedata record) {
        return this.yqgdTradedataMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(YqgdTradedata record) {
        return this.yqgdTradedataMapper.updateByPrimaryKey(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.yqgdTradedataMapper.deleteByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(YqgdTradedata record, Criteria example) {
        return this.yqgdTradedataMapper.updateByExampleSelective(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(YqgdTradedata record, Criteria example) {
        return this.yqgdTradedataMapper.updateByExample(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(YqgdTradedata record) {
        return this.yqgdTradedataMapper.insert(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(YqgdTradedata record) {
        return this.yqgdTradedataMapper.insertSelective(record);
    }
}