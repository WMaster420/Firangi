package com.vinayreddy.firangi.models;

public class LessonModel {

    private String sNo;
    private String lessonName;
    private boolean isTest;
    String lessonType;
    //private Integer lessonLength;
    //private Boolean isLocked;
    //private Boolean isCompleted;

    public LessonModel(){

    }

    /*public LessonModel(String sNo, String lessonName, Integer lessonLength, Boolean isLocked, Boolean isCompleted, String isTest){
        this.sNo = sNo;
        this.lessonName = lessonName;
        if(lessonLength != null)
            this.lessonLength = lessonLength;
        if(isLocked != null)
            this.isLocked = isLocked;
        if(isCompleted != null)
            this.isCompleted = isCompleted;
        this.isTest = isTest;
    }*/

    public LessonModel(String sNo, String lessonName, boolean isTest){
        this.sNo = sNo;
        this.lessonName = lessonName;
        this.isTest = isTest;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /*public Integer getLessonLength() {
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
*/
    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }
}
