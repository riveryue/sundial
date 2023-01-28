package com.yao.dto;

import lombok.Data;

/**
 * @author yao
 */
@Data
public class TaskConfDTO {

    private Integer id;

    private String taskName;

    private Integer status;

    private String cron;
}
