package sundial.annotation;

import java.lang.annotation.*;

/**
 * @author yao
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SundialTask {

    String name();

}
