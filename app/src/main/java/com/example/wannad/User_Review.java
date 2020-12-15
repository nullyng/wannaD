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
    String img;

    public User_Review(String cname, String dname, String context, float star, String time, String img){
        this.cname = cname;
        this.dname = dname;
        this.context = context;
        this.star = star;
        this.time = time;
        this.img = img;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("cname",cname);
        result.put("dname",dname);
        result.put("star",star);
        result.put("context",context);
        result.put("time",time);
        result.put("image",img);
        return result;
    }
}

