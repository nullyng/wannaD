package com.example.wannad;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Review {
    String name;
    String context;
    float star;
    String time;

    public Review(String name, String context, float star, String time){
        this.name = name;
        this.context = context;
        this.star = star;
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("star",star);
        result.put("context",context);
        result.put("time",time);
        return result;
    }

    public String getName(){
        return name;
    }

    public String getContext(){
        return context;
    }

    public String getTime(){return time;}

    public float getStar(){
        return star;
    }
}
