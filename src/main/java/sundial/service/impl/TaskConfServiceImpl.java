package sundial.service.impl;

import sundial.dao.TaskConfDao;
import sundial.dto.TaskConfDTO;
import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import sundial.service.TaskConfService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yao
 */
@Service
public class TaskConfServiceImpl implements TaskConfService {

    @Autowired
    private TaskConfDao taskConfDao;

    @Override
    public TaskConfDTO query(TaskConfQuery taskConfQuery) {
        TaskConfDO taskConfDO = taskConfDao.query(taskConfQuery);
        TaskConfDTO taskConfDTO = new TaskConfDTO();
        BeanUtils.copyProperties(taskConfDO, taskConfDTO);
        return taskConfDTO;
    }
}
