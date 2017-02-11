package com.cjam.springboot.appEntity;

/**
 * Created by jam on 2017/2/8.
 */
public class FeedbackLog {

    private long id;
    private String content;
    /**
     * 0: 未处理
     * 200：已处理
     */
    private int status;
    private long createTime;
    private long updateTime;
    private long courseLogId;
    /**
     * 评分
     */
    private int score = -1;

    /**
     * 反馈类型，分为学生课程反馈，老师课程反馈，普通反馈
     */
    private int type = 0;
    private long userId;

    public FeedbackLog(long userId,String content, long courseLogId, int score) {
        this.userId = userId;
        this.content = content;
        this.courseLogId = courseLogId;
        this.score = score;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCourseLogId() {
        return courseLogId;
    }

    public void setCourseLogId(long courseLogId) {
        this.courseLogId = courseLogId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
