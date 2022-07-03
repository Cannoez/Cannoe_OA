package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.service.LeaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-20 19:33
 */
@SpringBootTest
class LeaveServiceImplTest {
    @Autowired
    LeaveService leaveService;
    @Test
    void audit() {
        leaveService.audit(31L,2L,"approved","祝早日康复");
    }

    @Test
    void audit2() {
        leaveService.audit(32L,2L,"refused","工期紧张,请勿拖延");
    }

    @Test
    void audit3() {
        leaveService.audit(33L,1L,"approved","同意");
    }
}