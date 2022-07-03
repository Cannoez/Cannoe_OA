package com.zeal.zeal_oa.model.dao;

import com.zeal.zeal_oa.model.pojo.RoleUser;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserMapper {
    int deleteByPrimaryKey(Long ruId);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Long ruId);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
}