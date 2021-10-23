package sundial.service;

import sundial.dto.TaskConfDTO;
import sundial.query.TaskConfQuery;

import java.util.List;

/**
 * @author yao
 */
public interface TaskConfService {

    TaskConfDTO queryByTaskName(String taskName);

    List<TaskConfDTO> query(TaskConfQuery taskConfQuery);
}
