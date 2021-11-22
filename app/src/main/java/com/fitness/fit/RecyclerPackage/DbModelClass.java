package com.fitness.fit.RecyclerPackage;

public class DbModelClass {
    String planDes;
    String D_for;
    String D_type;

    public DbModelClass(String planDes,String d_for,String D_type)
    {
        this.planDes = planDes;
        this.D_for = d_for;
        this.D_type = D_type;
    }

    public String getPlanDes() {
        return planDes;
    }
    public String getD_for() { return D_for; }
    public String getD_type() { return D_type; }
    public void setPlanDes(String planDes) {
        this.planDes = planDes;
    }
    public void setD_for(String D_for) {
        this.D_for = D_for;
    }
    public void setD_type(String D_type) {
        this.D_type = D_type;
    }
}
