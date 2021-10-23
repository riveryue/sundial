package sundial.service.impl;

import sundial.dao.TaskConfDao;
import sundial.dto.TaskConfDTO;
import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 */
@Service
public class TaskConfServiceImpl implements TaskConfService {

    @Autowired
    private TaskConfDao taskConfDao;

    @Override
    public TaskConfDTO queryByTaskName(String taskName) {
        TaskConfDO taskConfDO = taskConfDao.queryByTaskName(taskName);
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
}
