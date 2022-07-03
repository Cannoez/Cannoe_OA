package com.zeal.zeal_oa.service;

import com.zeal.zeal_oa.model.pojo.Node;
import com.zeal.zeal_oa.model.pojo.User;

import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-17 17:04
 */
public interface UserService {
    User checkLogin(String username, String password);

    List<Node> selectNodeByUserId(Long userId);
}
