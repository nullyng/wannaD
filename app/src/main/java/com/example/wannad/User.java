package com.example.wannad;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String name;
    public String profile;

    public User(String name, String profile){
        this.name = name;
        this.profile = profile;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("profile",profile);

        return result;
    }
}
