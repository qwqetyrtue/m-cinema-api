package cn.hnist.sharo.mcinema.core.schedule;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务管理器
 */
@Component
public class TaskManager implements DisposableBean {
    // 存放定时任务
    private final Map<Runnable, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>(16);

    // 定时任务管理
    @Resource
    TaskScheduler taskScheduler;

    public int addTask(Runnable runnable,String cronString){
        return this.addTask(new CronTask(runnable, cronString));
    }

    public int addTask(CronTask task){
        if(task == null)
            return -1;
        return -1;
    }



    // 销毁时需要取消所有的定时任务
    @Override
    public void destroy() throws Exception {

    }

    public Map<Runnable, ScheduledFuture<?>> getTaskMap() {
        return taskMap;
    }
}
