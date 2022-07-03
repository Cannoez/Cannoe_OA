package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.exception.ZealOAException;
import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;
import com.zeal.zeal_oa.model.dao.NodeMapper;
import com.zeal.zeal_oa.model.dao.UserMapper;
import com.zeal.zeal_oa.model.pojo.Node;
import com.zeal.zeal_oa.model.pojo.User;
import com.zeal.zeal_oa.service.UserService;
import com.zeal.zeal_oa.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:用户模块实现类
 * @date: 2022-06-17 17:04
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    NodeMapper nodeMapper;

    /**
     * 根据前台输入进行登录校验
     * @param username 前台输入的用户名
     * @param password 前台输入的密码
     * @return 校验通过后,包含对应用户数据和User实体类
     */
    @Override
    public User checkLogin(String username, String password){
        //密码校验
        String md5password=null;
        try {
            md5password= MD5Util.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User result = userMapper.selectByName(username);
        if (result==null){
            throw new ZealOAException(ZealOAExceptionEnum.WRONG_USERNAME);
        }
        if (!md5password.equals(result.getPassword())){
            throw new ZealOAException(ZealOAExceptionEnum.WRONG_PASSWORD);
        }
        return result;
    }

    @Override
    public List<Node> selectNodeByUserId(Long userId){
        List<Node> nodeList = nodeMapper.selectNodeByUserId(userId);
        for (int i = 0; i < nodeList.size(); i++) {
            Node node =  nodeList.get(i);
            System.out.println(node.getNodeName());
        }
        return nodeList;
    }



}
