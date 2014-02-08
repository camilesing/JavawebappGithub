package com.authority.service.impl;

import com.authority.dao.WzgdShoulywnrMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdShoulywnr;
import com.authority.service.WzgdShoulywnrService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WzgdShoulywnrServiceImpl implements WzgdShoulywnrService {
    @Autowired
    private WzgdShoulywnrMapper wzgdShoulywnrMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzgdShoulywnrServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzgdShoulywnrMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzgdShoulywnr selectByPrimaryKey(String id) {
        return this.wzgdShoulywnrMapper.selectByPrimaryKey(id);
    }

    public List<WzgdShoulywnr> selectByExample(Criteria example) {
        return this.wzgdShoulywnrMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.wzgdShoulywnrMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WzgdShoulywnr record) {
        return this.wzgdShoulywnrMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WzgdShoulywnr record) {
        return this.wzgdShoulywnrMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.wzgdShoulywnrMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(WzgdShoulywnr record, Criteria example) {
        return this.wzgdShoulywnrMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(WzgdShoulywnr record, Criteria example) {
        return this.wzgdShoulywnrMapper.updateByExample(record, example);
    }

    public int insert(WzgdShoulywnr record) {
        return this.wzgdShoulywnrMapper.insert(record);
    }

    public int insertSelective(WzgdShoulywnr record) {
        return this.wzgdShoulywnrMapper.insertSelective(record);
    }
}