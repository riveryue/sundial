package sundial.controller;

import org.springframework.web.bind.annotation.*;
import sundial.TaskPool;
import sundial.SundialExecute;
import org.springframework.beans.factory.annotation.Autowired;
import sundial.base.Result;
import sundial.constant.TaskStatusEnum;
import sundial.dto.TaskConfDTO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;

import java.util.List;

/**
 * @author yao
 */
@RestController
public class TaskController {

    @Autowired
    private TaskPool taskPool;

    @Autowired
    private TaskConfService taskConfService;

    @GetMapping("/sundial/list")
    public Result<TaskConfDTO> taskList() {
        List<TaskConfDTO> taskConfDTOS = taskConfService.query(new TaskConfQuery());
        return Result.ok(taskConfDTOS);
    }

    @PostMapping("/sundial/save")
    public Result<Boolean> saveTask(@RequestBody TaskConfDTO taskConfDTO) {
        Integer res = taskConfService.insert(taskConfDTO);
        return res.equals(TaskStatusEnum.ENABLE.getStatus()) ? Result.ok(true) : Result.ok(false);
    }

    @PutMapping("/sundial/update")
    public Result<Boolean> update(@RequestBody TaskConfDTO taskConfDTO) {
        Integer res = taskConfService.update(taskConfDTO);
        return res.equals(TaskStatusEnum.ENABLE.getStatus()) ? Result.ok(true) : Result.ok(false);
    }

    @GetMapping("/sundial/execute")
    public Result<Boolean> executeTask(String taskName) {
        SundialExecute task = taskPool.get(taskName);
        task.execute();
        return Result.ok(true);
    }



}