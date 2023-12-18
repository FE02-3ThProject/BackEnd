package com.github.gather.dto.response;

public class IntroductionResponse {

    private String selfIntroduction;

    public IntroductionResponse() {
    }

    public IntroductionResponse(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

}
