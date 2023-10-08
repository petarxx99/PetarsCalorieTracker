package com.PetarsCalorieTracker.controllers;

public class MyResponse {

    public final boolean responseIsOk;
    public final String title;
    public final String content;

    public final Object response;

    public static MyResponse negative(String title, String content){
        return new MyResponse(false, title, content, null);
    }

    public static MyResponse positive(){
        return new MyResponse(true, null, null, null);
    }

    public static MyResponse positive(Object response){
        return new MyResponse(true, null, null, response);
    }
    private MyResponse(boolean responseIsOk, String title, String content, Object response){
        this.responseIsOk = responseIsOk;
        this.title = title;
        this.content = content;
        this.response = response;
    }
}
