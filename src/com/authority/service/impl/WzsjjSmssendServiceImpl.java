package com.authority.service.impl;


import com.authority.dao.WzsjjSmssendMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzsjjSmssend;
import com.authority.service.WzsjjSmssendService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service
public class WzsjjSmssendServiceImpl implements WzsjjSmssendService {
    @Autowired
    private WzsjjSmssendMapper wzsjjSmssendMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzsjjSmssendServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzsjjSmssendMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzsjjSmssend selectByPrimaryKey(String id) {
        return this.wzsjjSmssendMapper.selectByPrimaryKey(id);
    }

    public List<WzsjjSmssend> selectByExample(Criteria example) {
        return this.wzsjjSmssendMapper.selectByExample(example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByPrimaryKey(String id) {
        return this.wzsjjSmssendMapper.deleteByPrimaryKey(id);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKeySelective(WzsjjSmssend record) {
        return this.wzsjjSmssendMapper.updateByPrimaryKeySelective(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(WzsjjSmssend record) {
        return this.wzsjjSmssendMapper.updateByPrimaryKey(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int deleteByExample(Criteria example) {
        return this.wzsjjSmssendMapper.deleteByExample(example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(WzsjjSmssend record, Criteria example) {
        return this.wzsjjSmssendMapper.updateByExampleSelective(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(WzsjjSmssend record, Criteria example) {
        return this.wzsjjSmssendMapper.updateByExample(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(WzsjjSmssend record) {
        return this.wzsjjSmssendMapper.insert(record);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insertSelective(WzsjjSmssend record) {
        return this.wzsjjSmssendMapper.insertSelective(record);
    }
}