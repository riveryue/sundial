package sundial;

import sundial.annotation.SundialTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yao
 */
@Component
@Slf4j
public class Task2 implements SundialExecute {

    @SundialTask(name = "test2")
    @Override
    public void execute() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            log.error("error in schedule ", e);
        }
        log.info("test2 execute successfully");
    }

}
