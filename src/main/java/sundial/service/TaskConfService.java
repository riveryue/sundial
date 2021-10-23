package sundial.service;

import sundial.dto.TaskConfDTO;
import sundial.query.TaskConfQuery;

/**
 * @author yao
 */
public interface TaskConfService {

    TaskConfDTO query(TaskConfQuery taskConfQuery);
}
