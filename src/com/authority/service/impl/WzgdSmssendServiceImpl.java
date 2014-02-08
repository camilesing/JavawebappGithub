package com.authority.service.impl;

import com.authority.dao.WzgdSmssendMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdSmssend;
import com.authority.service.WzgdSmssendService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WzgdSmssendServiceImpl implements WzgdSmssendService {
    @Autowired
    private WzgdSmssendMapper wzgdSmssendMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzgdSmssendServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzgdSmssendMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzgdSmssend selectByPrimaryKey(String id) {
        return this.wzgdSmssendMapper.selectByPrimaryKey(id);
    }

    public List<WzgdSmssend> selectByExample(Criteria example) {
        return this.wzgdSmssendMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.wzgdSmssendMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WzgdSmssend record) {
        return this.wzgdSmssendMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WzgdSmssend record) {
        return this.wzgdSmssendMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.wzgdSmssendMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(WzgdSmssend record, Criteria example) {
        return this.wzgdSmssendMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(WzgdSmssend record, Criteria example) {
        return this.wzgdSmssendMapper.updateByExample(record, example);
    }

    public int insert(WzgdSmssend record) {
        return this.wzgdSmssendMapper.insert(record);
    }

    public int insertSelective(WzgdSmssend record) {
        return this.wzgdSmssendMapper.insertSelective(record);
    }
}