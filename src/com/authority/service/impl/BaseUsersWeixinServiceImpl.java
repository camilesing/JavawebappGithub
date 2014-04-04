package com.authority.service.impl;

import com.authority.dao.BaseUsersWeixinMapper;
import com.authority.pojo.BaseUsersWeixin;
import com.authority.pojo.Criteria;
import com.authority.service.BaseUsersWeixinService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseUsersWeixinServiceImpl implements BaseUsersWeixinService {
    @Autowired
    private BaseUsersWeixinMapper baseUsersWeixinMapper;

    private static final Logger logger = LoggerFactory.getLogger(BaseUsersWeixinServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.baseUsersWeixinMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public BaseUsersWeixin selectByPrimaryKey(String id) {
        return this.baseUsersWeixinMapper.selectByPrimaryKey(id);
    }

    public List<BaseUsersWeixin> selectByExample(Criteria example) {
        return this.baseUsersWeixinMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.baseUsersWeixinMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(BaseUsersWeixin record) {
        return this.baseUsersWeixinMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(BaseUsersWeixin record) {
        return this.baseUsersWeixinMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.baseUsersWeixinMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(BaseUsersWeixin record, Criteria example) {
        return this.baseUsersWeixinMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(BaseUsersWeixin record, Criteria example) {
        return this.baseUsersWeixinMapper.updateByExample(record, example);
    }

    public int insert(BaseUsersWeixin record) {
        return this.baseUsersWeixinMapper.insert(record);
    }

    public int insertSelective(BaseUsersWeixin record) {
        return this.baseUsersWeixinMapper.insertSelective(record);
    }
}