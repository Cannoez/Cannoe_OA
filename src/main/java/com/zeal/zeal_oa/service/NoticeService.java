package com.zeal.zeal_oa.service;

import com.zeal.zeal_oa.model.pojo.Notice;

import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-20 21:19
 */
public interface NoticeService {
    List<Notice> getNoticeList(Long receiverId);
}
