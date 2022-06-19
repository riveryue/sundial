package com.yao;

import com.yao.annotation.SundialTask;
import com.yao.config.CuratorFrameworkConfig;
import com.yao.constant.TaskStatus;
import com.yao.dto.TaskConfDTO;
import com.yao.service.TaskConfService;
import com.yao.utils.SpringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author yao
 */
public interface SundialExecute extends Runnable {

    void execute();

    /**
     * valid status of job if available
     */
    @Override
    default void run() {
        TaskConfService taskConfService = SpringUtils.getBean(TaskConfService.class);
        Method[] declaredMethods = this.getClass().getDeclaredMethods();
        String annoVal = StringUtils.EMPTY;
        for (Method declaredMethod : declaredMethods) {
            boolean annotationPresent = declaredMethod.isAnnotationPresent(SundialTask.class);
            if (annotationPresent) {
                SundialTask methodAnno = declaredMethod.getAnnotation(SundialTask.class);
                annoVal = methodAnno.name();
            }
        }
        TaskConfDTO taskConfDTO = taskConfService.queryByTaskName(annoVal);
        if (taskConfDTO != null && taskConfDTO.getStatus().equals(TaskStatus.DISABLE)) {
            return;
        }

        String ZK_LOCK_PATH = "/distributeLock";
        CuratorFramework client = SpringUtils.getBean(CuratorFrameworkConfig.class).curatorFramework();
        final InterProcessMutex mutex = new InterProcessMutex(client, ZK_LOCK_PATH);
        try {
            //获取锁资源
            boolean flag = mutex.acquire(1, TimeUnit.SECONDS);
            if (flag) {
                execute();
            }
        } catch (Exception e) {

        } finally {
            try {
                mutex.release();
            } catch (Exception e) {

            }
        }

    }
}
