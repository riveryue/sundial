package com.yao;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author yao
 */
@Component
@Slf4j
public class TaskPool {

    private static HashMap<String, SundialExecute> taskPool = Maps.newHashMap();

    public void put(String taskName, SundialExecute task) {
        taskPool.put(taskName, task);
    }

    public SundialExecute get(String taskName) {
        return taskPool.get(taskName);
    }

    public void remove(String taskName) {
        taskPool.remove(taskName);
    }

    public SundialExecute replace(String taskName, SundialExecute sundialExecute) {
        return taskPool.replace(taskName, sundialExecute);
    }

    public HashMap<String, SundialExecute> getAll() {
        return taskPool;
    }
}
