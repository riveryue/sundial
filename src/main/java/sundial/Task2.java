package sundial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

import java.util.Date;

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
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.error("error in schedule ", e);
        }
        log.info("schedule execute successfully");
    }

}
