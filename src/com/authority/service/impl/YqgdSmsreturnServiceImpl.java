package com.authority.service.impl;

import com.authority.dao.YqgdSmsreturnMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdSmsreturn;
import com.authority.service.YqgdSmsreturnService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YqgdSmsreturnServiceImpl implements YqgdSmsreturnService {
    @Autowired
    private YqgdSmsreturnMapper yqgdSmsreturnMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdSmsreturnServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdSmsreturnMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdSmsreturn selectByPrimaryKey(String id) {
        return this.yqgdSmsreturnMapper.selectByPrimaryKey(id);
    }

    public List<YqgdSmsreturn> selectByExample(Criteria example) {
        return this.yqgdSmsreturnMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.yqgdSmsreturnMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(YqgdSmsreturn record) {
        return this.yqgdSmsreturnMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(YqgdSmsreturn record) {
        return this.yqgdSmsreturnMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.yqgdSmsreturnMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(YqgdSmsreturn record, Criteria example) {
        return this.yqgdSmsreturnMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(YqgdSmsreturn record, Criteria example) {
        return this.yqgdSmsreturnMapper.updateByExample(record, example);
    }

    public int insert(YqgdSmsreturn record) {
        return this.yqgdSmsreturnMapper.insert(record);
    }

    public int insertSelective(YqgdSmsreturn record) {
        return this.yqgdSmsreturnMapper.insertSelective(record);
    }
}