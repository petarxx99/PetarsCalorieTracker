package com.PetarsCalorieTracker.controllers;

public class MyResponse {

    public final boolean responseIsOk;
    public final String title;
    public final String content;

    public static MyResponse negative(String title, String content){
        return new MyResponse(false, title, content);
    }

    public static MyResponse positive(){
        return new MyResponse(true, null, null);
    }

    private MyResponse(boolean responseIsOk, String title, String content){
        this.responseIsOk = responseIsOk;
        this.title = title;
        this.content = content;
    }
}
