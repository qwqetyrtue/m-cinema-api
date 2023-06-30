package cn.hnist.sharo.mcinema.db.service.system;

import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.SystemLogMapper;
import cn.hnist.sharo.mcinema.db.pojo.SystemLog;
import cn.hnist.sharo.mcinema.db.pojo.SystemLogExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemLogService {
    @Resource
    SystemLogMapper systemLogMapper;

    public List<SystemLog> listByFilter(SystemLog filter, LocalDateTime timeUp, LocalDateTime timeDown) {
        SystemLogExample.Criteria criteria = SystemLogExample.newAndCreateCriteria();
        if (DataUtil.checkEmpty(filter.getLogType()))
            criteria.andLogTypeEqualTo(filter.getLogType());
        if (DataUtil.checkEmpty(filter.getAdminId()))
            criteria.andAdminIdEqualTo(filter.getAdminId());
        if (DataUtil.checkEmpty(filter.getIp()))
            criteria.andIpEqualTo(filter.getIp());
        if (DataUtil.checkEmpty(timeUp))
            criteria.andTimeLessThanOrEqualTo(timeUp);
        if (DataUtil.checkEmpty(timeDown))
            criteria.andTimeGreaterThanOrEqualTo(timeDown);
        return systemLogMapper.selectByExample(criteria.example());
    }

    public SystemLog selectOne(Long logId){
        return systemLogMapper.selectByPrimaryKey(logId);
    }

    public int delete(Long logId){
        return systemLogMapper.logicalDeleteByPrimaryKey(logId);
    }

    public int insert(SystemLog log){
        return systemLogMapper.insertSelective(log);
    }
}
