package sundial.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sundial.SundialExecute;
import sundial.TaskPool;
import sundial.config.ScheduledConfig;
import sundial.dao.TaskConfDao;
import sundial.dto.TaskConfDTO;
import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;

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

    @Autowired
    private ScheduledConfig scheduledConfig;

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
        SundialExecute job = taskPool.get(existTask.getTaskName());
        if (job != null) {
            scheduledConfig.restartJob(job, taskConfDTO);
        }
        taskPool.remove(existTask.getTaskName());
        taskPool.put(existTask.getTaskName(), job);
        return taskConfDao.update(taskConfDO);
    }
}
