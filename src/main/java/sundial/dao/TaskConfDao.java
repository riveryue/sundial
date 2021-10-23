package sundial.dao;

import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yao
 */
@Component
public interface TaskConfDao {

    TaskConfDO queryByTaskName(String taskName);

    List<TaskConfDO> query(TaskConfQuery taskConfQuery);
}
