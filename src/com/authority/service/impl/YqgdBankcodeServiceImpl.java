package com.authority.service.impl;

import com.authority.dao.YqgdBankcodeMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.YqgdBankcode;
import com.authority.service.YqgdBankcodeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YqgdBankcodeServiceImpl implements YqgdBankcodeService {
    @Autowired
    private YqgdBankcodeMapper yqgdBankcodeMapper;

    private static final Logger logger = LoggerFactory.getLogger(YqgdBankcodeServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.yqgdBankcodeMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public YqgdBankcode selectByPrimaryKey(String id) {
        return this.yqgdBankcodeMapper.selectByPrimaryKey(id);
    }

    public List<YqgdBankcode> selectByExample(Criteria example) {
        return this.yqgdBankcodeMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.yqgdBankcodeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(YqgdBankcode record) {
        return this.yqgdBankcodeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(YqgdBankcode record) {
        return this.yqgdBankcodeMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.yqgdBankcodeMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(YqgdBankcode record, Criteria example) {
        return this.yqgdBankcodeMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(YqgdBankcode record, Criteria example) {
        return this.yqgdBankcodeMapper.updateByExample(record, example);
    }

    public int insert(YqgdBankcode record) {
        return this.yqgdBankcodeMapper.insert(record);
    }

    public int insertSelective(YqgdBankcode record) {
        return this.yqgdBankcodeMapper.insertSelective(record);
    }
}