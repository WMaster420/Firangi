package com.vinayreddy.firangi.models;

public class UserModel {
    private String userId;
    private String userName;
    private String userEmail;
    private String imageUrl;
    private String currentLevel;
    private Integer currentLesson;
    private Boolean isBeginnerCompleted;
    private Boolean isIntermediateCompleted;
    private Boolean isExpertCompleted;
    private String beginnerScore;
    private String intermediateScore;
    private String expertScore;
    private String tournamentScore;

    private static UserModel instance;

    final static public String LEVEL_BASICS = "Basics";
    final static public String LEVEL_BEGINNER = "Beginner";
    final static public String LEVEL_INTERMEDIATE = "Intermediate";
    final static public String LEVEL_EXPERT = "Expert";

    public UserModel(){}

    public UserModel(String userId, String userName, String imageUrl, String userEmail, String currentLevel,
                     Integer currentLesson, Boolean isBeginnerCompleted, Boolean isIntermediateCompleted,
                     Boolean isExpertCompleted, String beginnerScore, String intermediateScore, String expertScore,
                     String tournamentScore) {
        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.userEmail = userEmail;
        this.currentLevel = currentLevel;
        this.currentLesson = currentLesson;
        this.isBeginnerCompleted = isBeginnerCompleted;
        this.isIntermediateCompleted = isIntermediateCompleted;
        this.isExpertCompleted = isExpertCompleted;
        this.beginnerScore = beginnerScore;
        this.intermediateScore = intermediateScore;
        this.expertScore = expertScore;
        this.tournamentScore = tournamentScore;
    }

    public static UserModel getInstance() {
        if(instance == null){
            instance = new UserModel();
        }
        return instance;
    }

    public void setInstance(UserModel instance) {
        UserModel.instance = instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Integer getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(Integer currentLesson) {
        this.currentLesson = currentLesson;
    }

    public Boolean getBeginnerCompleted() {
        return isBeginnerCompleted;
    }

    public void setBeginnerCompleted(Boolean beginnerCompleted) {
        isBeginnerCompleted = beginnerCompleted;
    }

    public Boolean getIntermediateCompleted() {
        return isIntermediateCompleted;
    }

    public void setIntermediateCompleted(Boolean intermediateCompleted) {
        isIntermediateCompleted = intermediateCompleted;
    }

    public Boolean getExpertCompleted() {
        return isExpertCompleted;
    }

    public void setExpertCompleted(Boolean expertCompleted) {
        isExpertCompleted = expertCompleted;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBeginnerScore() {
        return beginnerScore;
    }

    public void setBeginnerScore(String beginnerScore) {
        this.beginnerScore = beginnerScore;
    }

    public String getIntermediateScore() {
        return intermediateScore;
    }

    public void setIntermediateScore(String intermediateScore) {
        this.intermediateScore = intermediateScore;
    }

    public String getExpertScore() {
        return expertScore;
    }

    public void setExpertScore(String expertScore) {
        this.expertScore = expertScore;
    }

    public String getTournamentScore() {
        return tournamentScore;
    }

    public void setTournamentScore(String tournamentScore) {
        this.tournamentScore = tournamentScore;
    }
}
