package com.cjam.springboot.appEntity;

/**
 * Created by jam on 2017/1/19.
 */
public class User {


    public final static int STUDENT_TYPE = 1;
    public final static int TEACHER_TYPE = 2;


    private long id = 0L;
    private int status = 0;
    private String identityNumber = "";
    private String name = "";

    private String phone = "";

    private long createTime = 0L;
    private String headUrl = "";

    private String nickName;
    private String openId;
    /**
     * 0 : 游客；
     * 1 : 学生;
     * 2 : 家长
     * 3 ：老师
     * -100 ： 管理员
     */
    private int type = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
