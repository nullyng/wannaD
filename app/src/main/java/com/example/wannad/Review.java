package com.example.wannad;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Review {
    String name;
    String context;
    float star;

    public Review(String name, String context, float star){
        this.name = name;
        this.context = context;
        this.star = star;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("star",star);
        result.put("context",context);

        return result;
    }
}
