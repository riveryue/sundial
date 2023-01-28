package sundial.service;

import sundial.dto.TaskConfDTO;
import sundial.query.TaskConfQuery;

import java.util.List;

/**
 * @author yao
 */
public interface TaskConfService {

    TaskConfDTO queryByTaskName(String taskName);

    TaskConfDTO queryById(Integer id);

    List<TaskConfDTO> query(TaskConfQuery taskConfQuery);

    Integer insert(TaskConfDTO taskConfDTO);

    Integer update(TaskConfDTO taskConfDTO);
}
