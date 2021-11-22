package com.fitness.fit;

import java.util.Locale;

public class ExModel {
    String title, anim;

    public ExModel(String title, String anim) {
        this.title = title;
        anim = anim.toLowerCase() + ".json";
        anim = anim.replaceAll("\\s", "");
        this.anim = anim;
    }

    public String getTitle() {        return title;    }

    public void setTitle(String title) {
        this.title = title;    }

    public String getAnim() {    return anim;    }

    public void setAnim(String anim) {        this.anim = anim; }
}
