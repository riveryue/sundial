package sundial.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import sundial.SundialExecute;
import sundial.TaskPool;
import sundial.annotation.SundialTask;
import sundial.dto.TaskConfDTO;
import sundial.service.TaskConfService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author yao
 */
@Slf4j
@Configuration
public class ScheduledConfig implements ApplicationContextAware, SmartInitializingSingleton, SchedulingConfigurer {

    private static ApplicationContext applicationContext;

    private static final HashSet<String> TASK_NAME_LIST = new HashSet<>();

    @Autowired
    private TaskPool taskPool;

    @Autowired
    private TaskConfService taskConfService;

    private TaskScheduler taskScheduler;

    private static HashMap<SundialExecute, String> methodHashMap = Maps.newHashMap();

    private Map<String, ScheduledFuture<?>> scheduledFutures = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ScheduledConfig.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (applicationContext == null) {
            return;
        }
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(SundialExecute.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Map<Method, SundialTask> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        (MethodIntrospector.MetadataLookup<SundialTask>) method -> AnnotatedElementUtils.findMergedAnnotation(method, SundialTask.class));
            } catch (Throwable ex) {
                log.error("task resolve error for bean[" + beanDefinitionName + "].", ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }
            for (Map.Entry<Method, SundialTask> taskEntry : annotatedMethods.entrySet()) {
                Method executeMethod = taskEntry.getKey();
                SundialTask task = taskEntry.getValue();
                registerJobHandler(task, bean, executeMethod);
                methodHashMap.put((SundialExecute) bean, task.name());
            }
        }
    }

    protected void registerJobHandler(SundialTask sundialTask, Object bean, Method executeMethod) {
        if (sundialTask == null) {
            return;
        }
        String taskName = sundialTask.name();
        Class<?> clazz = bean.getClass();
        String methodName = executeMethod.getName();
        if (taskName.trim().isEmpty()) {
            throw new RuntimeException("task name invalid, for[" + clazz + "#" + methodName + "] .");
        }
        if (!TASK_NAME_LIST.add(taskName)) {
            throw new RuntimeException("task can't be duplicate in whole project, [" + taskName + "] already exist.");
        }
        SundialExecute job = null;
        try {
            job = (SundialExecute) clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            log.error("occur error in create object via reflect ", e);
            //todo create custom exception
            throw new RuntimeException("occur error in create object via reflect");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("occur error in call method via reflect ", e);
        }
        taskPool.put(taskName, job);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskScheduler = taskRegistrar.getScheduler();
        for (Map.Entry<SundialExecute, String> entry : methodHashMap.entrySet()) {
            ScheduledFuture<?> future = taskScheduler.schedule(entry.getKey(), triggerContext -> {
                TaskConfDTO taskConfDTO = taskConfService.queryByTaskName(entry.getValue());
                return new CronTrigger(taskConfDTO.getCron()).nextExecutionTime(triggerContext);
            });
            scheduledFutures.put(entry.getValue(), future);
        }
    }

    public void reSchedule(SundialExecute job, TaskConfDTO taskConfDTO) {
        ScheduledFuture<?> future = scheduledFutures.get(taskConfDTO.getTaskName());
        if (future != null) {
            future.cancel(true);
        }
        ScheduledFuture<?> newFuture = taskScheduler.schedule(job, triggerContext -> new CronTrigger(taskConfDTO.getCron()).nextExecutionTime(triggerContext));
        scheduledFutures.put(taskConfDTO.getTaskName(), newFuture);
    }

}
