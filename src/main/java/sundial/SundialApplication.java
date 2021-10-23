package sundial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yao
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("sundial.dao")
public class SundialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SundialApplication.class, args);
	}

}
