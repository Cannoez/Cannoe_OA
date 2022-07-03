package com.zeal.zeal_oa.model.dao;

import com.zeal.zeal_oa.model.pojo.Process;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessMapper {
    int deleteByPrimaryKey(Long processId);

    int insert(Process record);

    int insertSelective(Process record);

    Process selectByPrimaryKey(Long processId);

    int updateByPrimaryKeySelective(Process record);

    int updateByPrimaryKey(Process record);

    List<Process> selectByFormId(Long formId);
}