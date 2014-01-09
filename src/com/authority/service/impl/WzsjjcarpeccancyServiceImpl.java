package com.authority.service.impl;

import com.authority.dao.WzsjjcarpeccancyMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.Wzsjjcarpeccancy;
import com.authority.service.WzsjjcarpeccancyService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WzsjjcarpeccancyServiceImpl implements WzsjjcarpeccancyService {
    @Autowired
    private WzsjjcarpeccancyMapper wzsjjcarpeccancyMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzsjjcarpeccancyServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzsjjcarpeccancyMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Wzsjjcarpeccancy selectByPrimaryKey(String id) {
        return this.wzsjjcarpeccancyMapper.selectByPrimaryKey(id);
    }

    public List<Wzsjjcarpeccancy> selectByExample(Criteria example) {
        return this.wzsjjcarpeccancyMapper.selectByExample(example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.wzsjjcarpeccancyMapper.deleteByPrimaryKey(id);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(Wzsjjcarpeccancy record) {
        return this.wzsjjcarpeccancyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(Wzsjjcarpeccancy record) {
        return this.wzsjjcarpeccancyMapper.updateByPrimaryKey(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.wzsjjcarpeccancyMapper.deleteByExample(example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(Wzsjjcarpeccancy record, Criteria example) {
        return this.wzsjjcarpeccancyMapper.updateByExampleSelective(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(Wzsjjcarpeccancy record, Criteria example) {
        return this.wzsjjcarpeccancyMapper.updateByExample(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(Wzsjjcarpeccancy record) {
        return this.wzsjjcarpeccancyMapper.insert(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(Wzsjjcarpeccancy record) {
        return this.wzsjjcarpeccancyMapper.insertSelective(record);
    }
}