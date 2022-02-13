//package com.whale.admin.controller;
//
//import com.whale.admin.async.AsyncTasks;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.concurrent.CompletableFuture;
//
//@Slf4j
//@SpringBootTest
//public class AsyncTaskTest {
//
//    @Autowired
//    private AsyncTasks asyncTasks;
//
//    @Test
//    public void test() throws Exception {
//
//        long start = System.currentTimeMillis();
//
//        asyncTasks.doTaskOne();
//        asyncTasks.doTaskTwo();
//        asyncTasks.doTaskThree();
//
//        CompletableFuture<String> taskFour = asyncTasks.doTaskFour();
//        CompletableFuture.allOf(taskFour).join();
//        long end = System.currentTimeMillis();
//        log.info("任务全部完成，总耗时：" + (end - start) + "毫秒");
//    }
//
//}
