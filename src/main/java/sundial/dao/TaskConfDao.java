package sundial.dao;

import sundial.entity.TaskConfDO;
import sundial.query.TaskConfQuery;
import org.springframework.stereotype.Component;

/**
 * @author yao
 */
@Component
public interface TaskConfDao {

    TaskConfDO query(TaskConfQuery taskConfQuery);
}
