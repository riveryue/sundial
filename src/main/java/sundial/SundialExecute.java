package sundial;

import sundial.annotation.SundialTask;
import sundial.dto.TaskConfDTO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;
import sundial.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

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
        //todo create enum
        if (taskConfDTO.getStatus().equals(2)) {
            return;
        }
        execute();
    }
}
