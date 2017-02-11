package com.cjam.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.FeedbackLog;
import com.cjam.springboot.mapper.FeedbackLogDAO;

/**
 * Created by jam on 2017/2/9.
 */
@Service
public class FeedbackLogService {

    @Autowired
    private FeedbackLogDAO feedbackLogDAO;


    public int addLog(FeedbackLog feedbackLog) throws BizException {
        if (feedbackLog == null){
            throw new BizException("feedbackLog == null");
        }
        int rs = feedbackLogDAO.insert(feedbackLog.getType(),feedbackLog.getUserId(),feedbackLog.getCourseLogId(), feedbackLog.getScore(),
                feedbackLog.getContent(), System.currentTimeMillis(), System.currentTimeMillis());
        if (rs <= 0){
            throw new BizException("feedbackLog insert fail!");
        }
        return 1;
    }
}
