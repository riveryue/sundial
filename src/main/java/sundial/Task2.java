package sundial;

import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

import java.util.Date;

/**
 * @author yao
 */
@Component
public class Task2 implements SundialExecute {

    @SundialTask(name = "test2")
    @Override
    public void execute() {
        System.out.println(new Date() + " task2");
    }

}
