package sundial.service.impl;

import sundial.SundialExecute;
import sundial.TaskPool;
import sundial.dao.TaskConfDao;
import sundial.dto.TaskConfDTO;
import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        TaskConfDTO existTask = this.queryById(taskConfDO.getId());
        SundialExecute sundialExecute = taskPool.get(existTask.getTaskName());
        taskPool.remove(existTask.getTaskName());
        taskPool.put(taskConfDO.getTaskName(), sundialExecute);
        return taskConfDao.update(taskConfDO);
    }
}
