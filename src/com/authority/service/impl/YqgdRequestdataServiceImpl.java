package com.authority.service.impl;

import com.authority.dao.YqgdRequestdataMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdRequestdata;
import com.authority.service.YqgdRequestdataService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YqgdRequestdataServiceImpl implements YqgdRequestdataService {
    @Autowired
    private YqgdRequestdataMapper yqgdRequestdataMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdRequestdataServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdRequestdataMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdRequestdata selectByPrimaryKey(String id) {
        return this.yqgdRequestdataMapper.selectByPrimaryKey(id);
    }

    public List<YqgdRequestdata> selectByExample(Criteria example) {
        return this.yqgdRequestdataMapper.selectByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.yqgdRequestdataMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(YqgdRequestdata record) {
        return this.yqgdRequestdataMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(YqgdRequestdata record) {
        return this.yqgdRequestdataMapper.updateByPrimaryKey(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.yqgdRequestdataMapper.deleteByExample(example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(YqgdRequestdata record, Criteria example) {
        return this.yqgdRequestdataMapper.updateByExampleSelective(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(YqgdRequestdata record, Criteria example) {
        return this.yqgdRequestdataMapper.updateByExample(record, example);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(YqgdRequestdata record) {
        return this.yqgdRequestdataMapper.insert(record);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(YqgdRequestdata record) {
        return this.yqgdRequestdataMapper.insertSelective(record);
    }
}