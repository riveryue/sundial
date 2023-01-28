package com.yao.dao;

import org.springframework.stereotype.Component;
import com.yao.entity.TaskConfDO;
import com.yao.query.TaskConfQuery;

import java.util.List;

/**
 * @author yao
 */
@Component
public interface TaskConfDao {

    TaskConfDO queryByTaskName(String taskName);

    TaskConfDO queryById(Integer id);

    List<TaskConfDO> query(TaskConfQuery taskConfQuery);

    Integer insert(TaskConfDO taskConfDO);

    Integer update(TaskConfDO taskConfDO);
}
