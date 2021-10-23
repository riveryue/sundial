package sundial.config;

import com.google.common.collect.Maps;
import sundial.TaskPool;
import sundial.SundialExecute;
import sundial.annotation.SundialTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author yao
 */
@Slf4j
@Configuration
public class ScheduledConfig implements ApplicationContextAware, SmartInitializingSingleton, SchedulingConfigurer {

    private static ApplicationContext applicationContext;

    private static HashSet<String> taskNameList = new HashSet<>();

    @Autowired
    private TaskPool taskPool;

    private static HashMap<SundialExecute, Method> methodHashMap = Maps.newHashMap();

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
                registJobHandler(task, bean, executeMethod);
                methodHashMap.put((SundialExecute) bean, executeMethod);
            }
        }
    }

    protected void registJobHandler(SundialTask sundialTask, Object bean, Method executeMethod) {
        if (sundialTask == null) {
            return;
        }
        String taskName = sundialTask.name();
        Class<?> clazz = bean.getClass();
        String methodName = executeMethod.getName();
        if (taskName.trim().length() == 0) {
            throw new RuntimeException("task name invalid, for[" + clazz + "#" + methodName + "] .");
        }
        if (!taskNameList.add(taskName)) {
            throw new RuntimeException("task can't be duplicate in whole project, [" + taskName + "] already exist.");
        }
        SundialExecute sundialExecute = null;
        try {
            sundialExecute = (SundialExecute) clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            log.error("occur error in create object via reflect ", e);
            //todo create custom exception
            throw new RuntimeException("occur error in create object via reflect");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        taskPool.put(taskName, sundialExecute);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //*/5 * * * * ?
        //todo get cron expression from mysql
        int gap = 5;
        for (Map.Entry<SundialExecute, Method> entry : methodHashMap.entrySet()) {
            String cron = "*/" + gap + " * * * * ?";
            taskRegistrar.addTriggerTask(entry.getKey(), triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext));
            gap += 3;
        }
    }
}
