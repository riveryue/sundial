package sundial.constant;

public enum TaskStatusEnum {

    ENABLE("ENABLE", 1),

    DISABLE("DISABLE", 2);

    private final String name;
    private final Integer status;

    TaskStatusEnum(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }
}
