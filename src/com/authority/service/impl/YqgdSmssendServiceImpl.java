package com.authority.service.impl;

import com.authority.dao.YqgdSmssendMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdSmssend;
import com.authority.service.YqgdSmssendService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YqgdSmssendServiceImpl implements YqgdSmssendService {
    @Autowired
    private YqgdSmssendMapper yqgdSmssendMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdSmssendServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdSmssendMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdSmssend selectByPrimaryKey(String id) {
        return this.yqgdSmssendMapper.selectByPrimaryKey(id);
    }

    public List<YqgdSmssend> selectByExample(Criteria example) {
        return this.yqgdSmssendMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.yqgdSmssendMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(YqgdSmssend record) {
        return this.yqgdSmssendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(YqgdSmssend record) {
        return this.yqgdSmssendMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.yqgdSmssendMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(YqgdSmssend record, Criteria example) {
        return this.yqgdSmssendMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(YqgdSmssend record, Criteria example) {
        return this.yqgdSmssendMapper.updateByExample(record, example);
    }

    public int insert(YqgdSmssend record) {
        return this.yqgdSmssendMapper.insert(record);
    }

    public int insertSelective(YqgdSmssend record) {
        return this.yqgdSmssendMapper.insertSelective(record);
    }
}