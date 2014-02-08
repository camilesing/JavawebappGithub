package com.authority.service.impl;

import com.authority.dao.WzgdSmsreturnMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdSmsreturn;
import com.authority.service.WzgdSmsreturnService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WzgdSmsreturnServiceImpl implements WzgdSmsreturnService {
    @Autowired
    private WzgdSmsreturnMapper wzgdSmsreturnMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzgdSmsreturnServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzgdSmsreturnMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzgdSmsreturn selectByPrimaryKey(String id) {
        return this.wzgdSmsreturnMapper.selectByPrimaryKey(id);
    }

    public List<WzgdSmsreturn> selectByExample(Criteria example) {
        return this.wzgdSmsreturnMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.wzgdSmsreturnMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WzgdSmsreturn record) {
        return this.wzgdSmsreturnMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WzgdSmsreturn record) {
        return this.wzgdSmsreturnMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.wzgdSmsreturnMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(WzgdSmsreturn record, Criteria example) {
        return this.wzgdSmsreturnMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(WzgdSmsreturn record, Criteria example) {
        return this.wzgdSmsreturnMapper.updateByExample(record, example);
    }

    public int insert(WzgdSmsreturn record) {
        return this.wzgdSmsreturnMapper.insert(record);
    }

    public int insertSelective(WzgdSmsreturn record) {
        return this.wzgdSmsreturnMapper.insertSelective(record);
    }
}