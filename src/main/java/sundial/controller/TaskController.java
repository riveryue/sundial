package sundial.controller;

import sundial.TaskPool;
import sundial.SundialExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yao
 */
@RestController
public class TaskController {

    @Autowired
    private TaskPool taskPool;

    @GetMapping("execute")
    public void getJob(String taskName) {
        SundialExecute test1 = taskPool.get(taskName);
        test1.execute();
    }
}