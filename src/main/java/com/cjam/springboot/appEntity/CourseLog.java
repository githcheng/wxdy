package com.cjam.springboot.appEntity;

import com.cjam.springboot.util.TimeUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by jam on 2017/2/9.
 * 课程记录
 */
public class CourseLog {

    private long id;
    private int type = 0;
    private int status = 0;
    private String name = "";
    private String studentName = "";
    private String teacherName = "";
    private long studentId = -1L;
    private int classId = -1;
    private String className = "";
    private long teacherId = -1L;
    private int courseId = -1;
    private long beginTime;
    private long endTime;
    private long createTime;
    private long updateTime;
    private String descMsg = "";


    public CourseLog(){}

    public CourseLog(String courseId, String studentId, String teacherId,
                     String courseName, String studentName, String teacherName,
                     String date, String beginTime, String endTime, String descmsg) throws BizException {

        this.courseId = Integer.valueOf(courseId);
        this.studentId = Long.valueOf(studentId);
        this.teacherId = Long.valueOf(teacherId);

        this.name = courseName;
        this.studentName = studentName;
        this.teacherName = teacherName;

        String newDate = date.replace('-', '/').trim();
        this.beginTime = TimeUtil.getLongTypeTime(newDate,beginTime.trim());
        this.endTime = TimeUtil.getLongTypeTime(newDate,endTime.trim());

        if (StringUtils.isNotBlank(descmsg)){
            this.descMsg = descmsg;
        }
    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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

    public String getDescMsg() {
        return descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
    }
}
