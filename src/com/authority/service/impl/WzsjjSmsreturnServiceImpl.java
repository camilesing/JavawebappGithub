package com.authority.service.impl;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.authority.dao.WzsjjSmsreturnMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzsjjSmsreturn;
import com.authority.service.WzsjjSmsreturnService;

@Service
public class WzsjjSmsreturnServiceImpl implements WzsjjSmsreturnService {
    @Autowired
    private WzsjjSmsreturnMapper wzsjjSmsreturnMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzsjjSmsreturnServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzsjjSmsreturnMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzsjjSmsreturn selectByPrimaryKey(String id) {
        return this.wzsjjSmsreturnMapper.selectByPrimaryKey(id);
    }

    public List<WzsjjSmsreturn> selectByExample(Criteria example) {
        return this.wzsjjSmsreturnMapper.selectByExample(example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.wzsjjSmsreturnMapper.deleteByPrimaryKey(id);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(WzsjjSmsreturn record) {
        return this.wzsjjSmsreturnMapper.updateByPrimaryKeySelective(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(WzsjjSmsreturn record) {
        return this.wzsjjSmsreturnMapper.updateByPrimaryKey(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.wzsjjSmsreturnMapper.deleteByExample(example);
    }
    
    
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(WzsjjSmsreturn record, Criteria example) {
        return this.wzsjjSmsreturnMapper.updateByExampleSelective(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(WzsjjSmsreturn record, Criteria example) {
        return this.wzsjjSmsreturnMapper.updateByExample(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(WzsjjSmsreturn record) {
        return this.wzsjjSmsreturnMapper.insert(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(WzsjjSmsreturn record) {
        return this.wzsjjSmsreturnMapper.insertSelective(record);
    }
}