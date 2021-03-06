package com.yao.service.impl;

import com.yao.dao.TaskConfDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yao.SundialExecute;
import com.yao.TaskPool;
import com.yao.dto.TaskConfDTO;
import com.yao.entity.TaskConfDO;
import com.yao.query.TaskConfQuery;
import com.yao.service.TaskConfService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 */
@Service
public class TaskConfServiceImpl implements TaskConfService {

    @Autowired
    private TaskConfDao taskConfDao;

    @Autowired
    private TaskPool taskPool;

    @Override
    public TaskConfDTO queryByTaskName(String taskName) {
        TaskConfDO taskConfDO = taskConfDao.queryByTaskName(taskName);
        if (taskConfDO == null) {
            return null;
        }
        TaskConfDTO taskConfDTO = new TaskConfDTO();
        BeanUtils.copyProperties(taskConfDO, taskConfDTO);
        return taskConfDTO;
    }

    @Override
    public TaskConfDTO queryById(Integer id) {
        TaskConfDO taskConfDO = taskConfDao.queryById(id);
        TaskConfDTO taskConfDTO = new TaskConfDTO();
        BeanUtils.copyProperties(taskConfDO, taskConfDTO);
        return taskConfDTO;
    }

    @Override
    public List<TaskConfDTO> query(TaskConfQuery taskConfQuery) {
        List<TaskConfDO> taskConfDOS = taskConfDao.query(taskConfQuery);
        return taskConfDOS.stream().map(taskConfDO -> {
            TaskConfDTO taskConfDTO = new TaskConfDTO();
            BeanUtils.copyProperties(taskConfDO, taskConfDTO);
            return taskConfDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer insert(TaskConfDTO taskConfDTO) {
        TaskConfDO taskConfDO = new TaskConfDO();
        BeanUtils.copyProperties(taskConfDTO, taskConfDO);
        return taskConfDao.insert(taskConfDO);
    }

    @Override
    public Integer update(TaskConfDTO taskConfDTO) {
        TaskConfDO taskConfDO = new TaskConfDO();
        BeanUtils.copyProperties(taskConfDTO, taskConfDO);
        System.out.println(taskConfDO);
        TaskConfDTO existTask = this.queryById(taskConfDO.getId());
        SundialExecute sundialExecute = taskPool.get(existTask.getTaskName());
        taskPool.remove(existTask.getTaskName());
        taskPool.put(existTask.getTaskName(), sundialExecute);
        return taskConfDao.update(taskConfDO);
    }
}
