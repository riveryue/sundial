package sundial.dao;

import org.springframework.stereotype.Component;
import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;

import java.util.List;

/**
 * @author yao
 */
@Component
public interface TaskLogDao {

    TaskConfDO queryByTaskName(String taskName);

    TaskConfDO queryById(Integer id);

    List<TaskConfDO> query(TaskConfQuery taskConfQuery);

    Integer insert(TaskConfDO taskConfDO);

    Integer update(TaskConfDO taskConfDO);
}
