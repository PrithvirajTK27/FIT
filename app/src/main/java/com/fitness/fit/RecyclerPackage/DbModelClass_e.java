package com.fitness.fit.RecyclerPackage;

public class DbModelClass_e {
    String plan_e;
    String e_for;
    String e_duration;

    public DbModelClass_e(String plan_e,String e_for,String e_duration)
    {
        this.e_duration = e_duration;
        this.e_for = e_for;
        this.plan_e = plan_e;
    }

    public String getPlane() {
        return plan_e;
    }
    public String getE_for() { return e_for; }
    public String getE_duration() { return e_duration; }
    public void setPlanDes(String planDes) {
        this.plan_e = plan_e;
    }
    public void setD_for(String D_for) {
        this.e_for = e_for;
    }
    public void setD_type(String D_type) {
        this.e_duration = e_duration;
    }
}
