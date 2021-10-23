package sundial;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author yao
 */
@Component
@Slf4j
public class TaskPool {

    private static HashMap<String, SundialExecute> jobPool = Maps.newHashMap();

    public void put(String jobName, SundialExecute task) {
        jobPool.put(jobName, task);
    }

    public SundialExecute get(String jobName) {
        return jobPool.get(jobName);
    }
}
