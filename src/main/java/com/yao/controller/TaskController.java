package com.yao.controller;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.yao.TaskPool;
import com.yao.SundialExecute;
import org.springframework.beans.factory.annotation.Autowired;
import com.yao.base.Result;
import com.yao.constant.TaskStatus;
import com.yao.dto.TaskConfDTO;
import com.yao.query.TaskConfQuery;
import com.yao.service.TaskConfService;

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