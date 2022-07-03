package com.zeal.zeal_oa.common;


import com.zeal.zeal_oa.exception.ZealOAException;
import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Sets;

import java.util.Set;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description: 常量值
 * @date: 2022-06-17 21:11
 */
@Component
public class Constant {
    public static final String ZEAL_OA_USER="zeal_mall_user";
    public static final String SALT="8svbsvjkweDF,.03[";
    public static final Integer sal=369;

    public static  final int MANAGER_AUDIT_HOURS=36;//总经理请假审批时间阈值
}
