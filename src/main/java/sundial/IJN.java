package sundial;

import org.springframework.stereotype.Component;
import sundial.annotation.SundialTask;

import java.util.Date;

@Component
public class IJN implements SundialExecute {

    @SundialTask(name = "test2")
    @Override
    public void execute() {
        Date date = new Date();
        System.out.println(date + " zxcv");
    }

}
