package cn.hnist.sharo.mcinema.adminapi.service;

import cn.hnist.sharo.mcinema.core.model.LogEnum;
import cn.hnist.sharo.mcinema.core.util.IPUtil;
import cn.hnist.sharo.mcinema.db.pojo.SystemLog;
import cn.hnist.sharo.mcinema.db.service.system.SystemLogService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service
public class LogService {
    @Resource
    SystemLogService systemLogService;
    @Resource
    private NativeWebRequest nativeWebRequest;

    public void successLog(LogEnum.Type type,Integer admin, LogEnum.Action action){
        saveLog(type,admin,action,true,null);
    }

    public void failLog(LogEnum.Type type,Integer admin,LogEnum.Action action,String result){
        saveLog(type,admin,action,false,result);
    }

    public void saveLog(LogEnum.Type type,Integer admin,LogEnum.Action action,boolean status,String result){
        SystemLog systemLog = new SystemLog();
        LocalDateTime time = LocalDateTime.now();
        if(nativeWebRequest != null)
            systemLog.setIp(IPUtil.getIPAddress(nativeWebRequest));
        systemLog.setTime(time);
        systemLog.setAction(action.getAction());
        systemLog.setStatus(status);
        systemLog.setResult(result);
        systemLog.setAdminId(admin);
        systemLog.setLogType(type.getRecord());
        systemLogService.insert(systemLog);
    }
}
