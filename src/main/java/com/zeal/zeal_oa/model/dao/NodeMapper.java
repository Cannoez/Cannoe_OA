package com.zeal.zeal_oa.model.dao;

import com.zeal.zeal_oa.model.pojo.Node;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeMapper {
    int deleteByPrimaryKey(Long nodeId);

    int insert(Node record);

    int insertSelective(Node record);

    Node selectByPrimaryKey(Long nodeId);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);

    List<Node> selectNodeByUserId(Long userId);
}