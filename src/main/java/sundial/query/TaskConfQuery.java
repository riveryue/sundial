package sundial.query;

/**
 * @author yao
 */
public class TaskConfQuery {

    private Integer id;

    private String taskName;

    private Integer status;

    private String cron;

    public TaskConfQuery addParamId(Integer id) {
        this.id = id;
        return this;
    }

    public TaskConfQuery addParamJobName(String jobName) {
        this.taskName = jobName;
        return this;
    }

    public TaskConfQuery addParamStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TaskConfQuery addParamCron(String cron) {
        this.cron = cron;
        return this;
    }
}
