package com.example.wannad;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User_Review {
    String cname;
    String dname;
    String context;
    float star;
    String time;


    public User_Review(String cname, String dname, String context, float star, String time){
        this.cname = cname;
        this.dname = dname;
        this.context = context;
        this.star = star;
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("cname",cname);
        result.put("dname",dname);
        result.put("star",star);
        result.put("context",context);
        result.put("time",time);
        return result;
    }
}

