package sundial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

@Slf4j
@Component
public class Task1 implements SundialExecute {

    @SundialTask(name = "test1")
    @Override
    public void execute() {
        log.info("test1 execute successfully");
    }

}
