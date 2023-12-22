package sundial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sundial.annotation.SundialTask;
import sundial.config.CuratorFrameworkConfig;
import sundial.constant.TaskStatus;
import sundial.dto.TaskConfDTO;
import sundial.service.TaskConfService;
import sundial.utils.SpringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author yao
 */
public interface SundialExecute extends Runnable {

    Logger logger = LoggerFactory.getLogger(SundialExecute.class);

    void execute();

    /**
     * valid status of schedule job if it's available
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

        String zkLockPath = "/distributeLock";
        CuratorFramework client = SpringUtils.getBean(CuratorFrameworkConfig.class).curatorFramework();
        final InterProcessMutex mutex = new InterProcessMutex(client, zkLockPath);
        try {
            //获取锁资源
            boolean flag = mutex.acquire(1, TimeUnit.SECONDS);
            if (flag) {
                execute();
                logger.info("Acquired lock successfully, executing task...");
            } else {
                logger.warn("Failed to acquire lock, task execution skipped.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while trying to acquire lock or execute task", e);
        } finally {
            try {
                mutex.release();
                logger.info("Lock released successfully.");
            } catch (Exception e) {
                logger.error("An error occurred while trying to release lock", e);
            }
        }
    }
}
