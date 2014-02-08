package com.authority.service.impl;

import com.authority.dao.WzgdKehjbxxMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.WzgdKehjbxx;
import com.authority.service.WzgdKehjbxxService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WzgdKehjbxxServiceImpl implements WzgdKehjbxxService {
    @Autowired
    private WzgdKehjbxxMapper wzgdKehjbxxMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzgdKehjbxxServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzgdKehjbxxMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public WzgdKehjbxx selectByPrimaryKey(String id) {
        return this.wzgdKehjbxxMapper.selectByPrimaryKey(id);
    }

    public List<WzgdKehjbxx> selectByExample(Criteria example) {
        return this.wzgdKehjbxxMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.wzgdKehjbxxMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(WzgdKehjbxx record) {
        return this.wzgdKehjbxxMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WzgdKehjbxx record) {
        return this.wzgdKehjbxxMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.wzgdKehjbxxMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(WzgdKehjbxx record, Criteria example) {
        return this.wzgdKehjbxxMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(WzgdKehjbxx record, Criteria example) {
        return this.wzgdKehjbxxMapper.updateByExample(record, example);
    }

    public int insert(WzgdKehjbxx record) {
        return this.wzgdKehjbxxMapper.insert(record);
    }

    public int insertSelective(WzgdKehjbxx record) {
        return this.wzgdKehjbxxMapper.insertSelective(record);
    }
}