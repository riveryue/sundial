package sundial.controller;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sundial.TaskPool;
import sundial.SundialExecute;
import org.springframework.beans.factory.annotation.Autowired;
import sundial.base.Result;
import sundial.constant.TaskStatus;
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

    @GetMapping("/sundial/home")
    public ModelAndView taskList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cronList", new Gson().toJson(taskConfService.query(new TaskConfQuery())));
        modelAndView.setViewName("task-list");
        return modelAndView;
    }

    @GetMapping("/sundial/list")
    public Result<TaskConfDTO> taskList() {
        List<TaskConfDTO> taskConfDTOS = taskConfService.query(new TaskConfQuery());
        return Result.ok(taskConfDTOS);
    }

    @PostMapping("/sundial/save")
    public Result<Boolean> saveTask(@RequestBody TaskConfDTO taskConfDTO) {
        Integer res = taskConfService.insert(taskConfDTO);
        return res.equals(TaskStatus.ENABLE) ? Result.ok(true) : Result.ok(false);
    }

    @PutMapping("/sundial/update")
    public Result<Boolean> update(@RequestBody TaskConfDTO taskConfDTO) {
        Integer res = taskConfService.update(taskConfDTO);
        return res.equals(TaskStatus.ENABLE) ? Result.ok(true) : Result.ok(false);
    }

    @GetMapping("/sundial/execute")
    public Result<Boolean> executeTask(String taskName) {
        SundialExecute task = taskPool.get(taskName);
        task.execute();
        return Result.ok(true);
    }



}