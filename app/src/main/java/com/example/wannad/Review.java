package com.example.wannad;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Review {
    String nickname;
    String context;
    float star;
    String time;


    public Review(){ }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickname",nickname);
        result.put("star",star);
        result.put("context",context);
        result.put("time",time);
        return result;
    }

    public String getNickname(){
        return nickname;
    }

    public String getContext(){
        return context;
    }

    public String getTime(){return time;}

    public float getStar(){
        return star;
    }

}
