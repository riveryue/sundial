package com.yao;

import com.yao.annotation.SundialTask;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task1 implements SundialExecute {

    @SundialTask(name = "test1")
    @Override
    public void execute() {
        Date date = new Date();
        System.out.println(date + " 1234");
    }

//    @QuartzJob(name = "test2")
//    public void print2() {
//        System.out.println("1234");
//    }
//
//    @QuartzJob(name = "test3")
//    public void print3() {
//        System.out.println("1234");
//    }

}
