package com.vinayreddy.firangi.models;

public class LessonContentModel {

    private String sNo;
    private String frenchWord;
    private String englishWord;
    private String frenchSentence;
    private String englishSentence;

    public LessonContentModel(){

    }

    public LessonContentModel(String sNo, String frenchWord, String englishWord, String frenchSentence, String englishSentence){
        this.sNo = sNo;
        this.frenchWord = frenchWord;
        this.englishWord = englishWord;
        this.frenchSentence = frenchSentence;
        this.englishSentence = englishSentence;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getFrenchWord() {
        return frenchWord;
    }

    public void setFrenchWord(String frenchWord) {
        this.frenchWord = frenchWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getFrenchSentence() {
        return frenchSentence;
    }

    public void setFrenchSentence(String frenchSentence) {
        this.frenchSentence = frenchSentence;
    }

    public String getEnglishSentence() {
        return englishSentence;
    }

    public void setEnglishSentence(String englishSentence) {
        this.englishSentence = englishSentence;
    }
}
