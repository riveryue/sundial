package com.yao.service;

import com.yao.dto.TaskConfDTO;
import com.yao.query.TaskConfQuery;

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
