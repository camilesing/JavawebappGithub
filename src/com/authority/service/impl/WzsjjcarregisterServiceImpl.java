package com.authority.service.impl;

import com.authority.dao.WzsjjcarregisterMapper;
import com.authority.pojo.Criteria;
import com.authority.pojo.Wzsjjcarregister;
import com.authority.service.WzsjjcarregisterService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WzsjjcarregisterServiceImpl implements WzsjjcarregisterService {
    @Autowired
    private WzsjjcarregisterMapper wzsjjcarregisterMapper;

    private static final Logger logger = LoggerFactory.getLogger(WzsjjcarregisterServiceImpl.class);

    public int countByExample(Criteria example) {
        int count = this.wzsjjcarregisterMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Wzsjjcarregister selectByPrimaryKey(String id) {
        return this.wzsjjcarregisterMapper.selectByPrimaryKey(id);
    }

    public List<Wzsjjcarregister> selectByExample(Criteria example) {
        return this.wzsjjcarregisterMapper.selectByExample(example);
    }


    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public String updateByPrimaryKeySelective(Wzsjjcarregister record) {
    	int result = 0;
		result = this.wzsjjcarregisterMapper.updateByPrimaryKeySelective(record);
		return result > 0 ? "01" : "00";
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByPrimaryKey(Wzsjjcarregister record) {
        return this.wzsjjcarregisterMapper.updateByPrimaryKey(record);
    }


    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExampleSelective(Wzsjjcarregister record, Criteria example) {
        return this.wzsjjcarregisterMapper.updateByExampleSelective(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int updateByExample(Wzsjjcarregister record, Criteria example) {
        return this.wzsjjcarregisterMapper.updateByExample(record, example);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int insert(Wzsjjcarregister record) {
        return this.wzsjjcarregisterMapper.insert(record);
    }


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveCar(Criteria example) {
		Wzsjjcarregister wzsjjcarregister = (Wzsjjcarregister) example.get("wzsjjcarregister");
		int result = 0;
		if (wzsjjcarregister.getId() == null) {
			result = this.wzsjjcarregisterMapper.insertSelective(wzsjjcarregister);
		} else {
			result = this.wzsjjcarregisterMapper.updateByPrimaryKeySelective(wzsjjcarregister);
		}
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String deleteByExample(Criteria example) {
		int result = 0;
		// 删除自己
		result = this.wzsjjcarregisterMapper.deleteByExample(example);
		return result > 0 ? "01" : "00";
	}


}