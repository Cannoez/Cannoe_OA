package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.model.dao.NoticeMapper;
import com.zeal.zeal_oa.model.pojo.Notice;
import com.zeal.zeal_oa.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-20 21:19
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNoticeList(Long receiverId){
        List<Notice> notices = noticeMapper.selectByReceiverId(receiverId);
        return notices;
    }
}
