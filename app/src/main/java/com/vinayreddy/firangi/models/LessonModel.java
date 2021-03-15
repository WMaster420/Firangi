package com.vinayreddy.firangi.models;

public class LessonModel {

    private String sNo;
    private String lessonName;
    private Integer lessonLength;
    private Boolean isLocked;
    private Boolean isCompleted;

    public LessonModel(){

    }

    public LessonModel(String sNo, String lessonName, Integer lessonLength, Boolean isLocked, Boolean isCompleted){
        this.sNo = sNo;
        this.lessonName = lessonName;
        if(lessonLength != null)
            this.lessonLength = lessonLength;
        if(isLocked != null)
            this.isLocked = isLocked;
        if(isCompleted != null)
            this.isCompleted = isCompleted;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getLessonLength() {
        return lessonLength;
    }

    public void setLessonLength(Integer lessonLength) {
        this.lessonLength = lessonLength;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }
}
