package com.zeal.zeal_oa.model.dao;

import com.zeal.zeal_oa.model.pojo.Leave;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LeaveMapper {
    int deleteByPrimaryKey(Long formId);

    int insert(Leave record);

    int insertSelective(Leave record);

    Leave selectByPrimaryKey(Long formId);

    int updateByPrimaryKeySelective(Leave record);

    int updateByPrimaryKey(Leave record);

    List<Map<String,Object>> selectByParam(@Param("state")String state,@Param("operatorId") Long operatorId);

    List<Map<String,Object>> selectByParam2(@Param("state")String state,@Param("operatorId") Long operatorId);
}