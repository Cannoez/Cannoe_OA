package com.zeal.zeal_oa.model.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-20 15:06
 */
@SpringBootTest
class LeaveMapperTest {
    @Autowired
    LeaveMapper leaveMapper;
    @Test
    void selectByParam() {
        List<Map<String, Object>> list = leaveMapper.selectByParam2("process",1l);
        System.out.println(list);
    }

    @Test
    void selectByParam2() {
    }
}