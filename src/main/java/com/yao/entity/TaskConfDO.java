package com.yao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 */
@Data
public class TaskConfDO implements Serializable {

    private Integer id;

    private String taskName;

    private Integer status;

    private String cron;
}
