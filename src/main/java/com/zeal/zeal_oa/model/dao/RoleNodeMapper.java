package com.zeal.zeal_oa.model.dao;

import com.zeal.zeal_oa.model.pojo.RoleNode;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleNodeMapper {
    int deleteByPrimaryKey(Long rnId);

    int insert(RoleNode record);

    int insertSelective(RoleNode record);

    RoleNode selectByPrimaryKey(Long rnId);

    int updateByPrimaryKeySelective(RoleNode record);

    int updateByPrimaryKey(RoleNode record);
}