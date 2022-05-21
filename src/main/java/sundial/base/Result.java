package sundial.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yao
 *
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2994494589314183496L;

    private boolean success;

    private T data;

    public static <T> Result ok(T data){
        System.out.println();
        Result<T> result = new Result<>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static Result ok(){
        int i = 0;
        return ok(null);
    }
}
