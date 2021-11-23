package sundial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

import java.util.Date;

@Component
@Slf4j
public class Task3 implements SundialExecute {

    @SundialTask(name = "test3")
    @Override
    public void execute() {

        log.info(new Date() + " task three executed");

    }

}
