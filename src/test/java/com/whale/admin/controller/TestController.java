//package com.whale.admin.controller;
//
//import com.whale.admin.web.system.repository.mapper.UserMapper;
//import com.whale.admin.web.system.service.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import com.whale.admin.web.system.repository.model.User;
//import java.util.List;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TestController {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    @Rollback
//    public void test() throws Exception {
//        userMapper.insert("AAA1", 20);
//        User u = userMapper.findByName("AAA1");
//        Assert.assertEquals(20, u.getAge().intValue());
//    }
//
//    /*@Autowired
//    private IUserService iUserService;
//
//    @Before
//    public void setUp() {
//        // 准备，清空user表
//        iUserService.deleteAllUsers();
//    }
//
//    @Test
//    public void test() throws Exception {
//        // 插入5个用户
//        iUserService.create("Tom", 10);
//        iUserService.create("Mike", 11);
//        iUserService.create("Didispace", 30);
//        iUserService.create("Oscar", 21);
//        iUserService.create("Linda", 17);
//
//        // 查询名为Oscar的用户，判断年龄是否匹配
//        List<User> userList = iUserService.getByName("Oscar");
//        Assert.assertEquals(21, userList.get(0).getAge().intValue());
//
//        // 查数据库，应该有5个用户
//        Assert.assertEquals(5, iUserService.getAllUsers());
//
//        // 删除两个用户
//        iUserService.deleteByName("Tom");
//        iUserService.deleteByName("Mike");
//
//        // 查数据库，应该有5个用户
//        Assert.assertEquals(3, iUserService.getAllUsers());
//
//    }*/
//
//}
