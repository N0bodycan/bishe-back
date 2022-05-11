package com.cy.homework;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.homework.entity.Student;
import com.cy.homework.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HomeworkBackApplicationTests {
    @Value("${file.upload.path}")
    private String path;

    @Test
    void contextLoads() {
    }


    @Autowired
    StudentMapper studentMapper;

//    @Test
//    void test1(){
//        IPage<Student> page = new Page<>(3,2);
//
//        IPage<Student> res = studentMapper.findStudent(page,"2018", "小");
//        if (res!= null){
//            List<Student> records = res.getRecords();
//            records.forEach(System.out::println);
//        }
//    }
//    @Test
//    void test2(){
//        System.out.println(this.path);
//    }

//    @Test
//    void test3(){
//        String s = SecureUtil.md5("123123");
//        System.out.println(s);
//        System.out.println(s.length());
//    }
}
