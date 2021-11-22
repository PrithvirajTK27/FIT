package com.fitness.fit.HomePackage;

import java.util.ArrayList;

public class DBModelPlan {
    String planDes;
    String D_for;
    String D_type;
    String plan_e;
    String e_for;
    String e_duration;
    String food;
    String d_n1;
    String d_n2;
    String d_n3;
    ArrayList<String> exercises;

    public DBModelPlan(String planDes, String d_for, String d_type, String plan_e, String e_for, String e_duration, String food, String d_n1, String d_n2, String d_n3, ArrayList<String> exercises) {
        this.planDes = planDes;
        D_for = d_for;
        D_type = d_type;
        this.plan_e = plan_e;
        this.e_for = e_for;
        this.e_duration = e_duration;
        d_n1 = d_n1.replace("#","\n");
        d_n2 = d_n2.replace("#","\n");
        d_n3 = d_n3.replace("#","\n");
        food = food.replace("#","\n");
        this.food = food;
        this.d_n1 = d_n1;
        this.d_n2 = d_n2;
        this.d_n3 = d_n3+"\n";
        this.exercises = exercises;
    }

    public ArrayList<String> getExercises() {        return exercises;    }

    public void setExercises(ArrayList<String> exercises) {
        this.exercises = exercises;
    }

    public String getD_n1() {         return d_n1;    }

    public void setD_n1(String d_n1) {        this.d_n1 = d_n1;    }

    public String getD_n2() {         return d_n2;    }

    public void setD_n2(String d_n2) {        this.d_n2 = d_n2;    }

    public String getD_n3() {         return d_n3;    }

    public void setD_n3(String d_n3) {        this.d_n3 = d_n3;    }
    public String getFood() {     return food; }
    public void setFood(String food) { this.food = food; }
    public String getPlanDes() { return planDes; }
    public void setPlanDes(String planDes) { this.planDes = planDes; }
    public String getD_for() { return D_for; }
    public void setD_for(String d_for) { D_for = d_for; }
    public String getD_type() { return D_type; }
    public void setD_type(String d_type) { D_type = d_type; }
    public String getPlan_e() { return plan_e; }
    public void setPlan_e(String plan_e) { this.plan_e = plan_e; }
    public String getE_for() { return e_for; }
    public void setE_for(String e_for) { this.e_for = e_for; }
    public String getE_duration() { return e_duration; }
    public void setE_duration(String e_duration) { this.e_duration = e_duration; }
}
