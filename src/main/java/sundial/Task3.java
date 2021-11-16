package sundial;

import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

import java.util.Date;

@Component
public class Task3 implements SundialExecute {

    @SundialTask(name = "test3")
    @Override
    public void execute() {
        Date date = new Date();
        System.out.println(date + " three");
    }

}
