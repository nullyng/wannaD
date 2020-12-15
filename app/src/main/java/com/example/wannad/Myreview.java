package com.example.wannad;

public class Myreview {
    String cname;
    String dname;
    float star;
    String context;
    String time;

    public Myreview() {  }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCname() {
        return cname;
    }

    public String getDname() {
        return dname;
    }

    public float getStar() {
        return star;
    }

    public String getContext() {
        return context;
    }

    public String getTime() {
        return time;
    }
}
