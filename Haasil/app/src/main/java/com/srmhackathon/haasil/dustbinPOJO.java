package com.srmhackathon.haasil;

/**
 * Created by Ravi on 06-10-2017.
 */

public class dustbinPOJO {

    public dustbinPOJO(String dustbinID, String percentage){
        this.dustbinID = dustbinID;
        this.percentage = percentage;
    }


    public String getDustbinID() {
        return dustbinID;
    }

    public void setDustbinID(String dustbinID) {
        this.dustbinID = dustbinID;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    String dustbinID;
    String percentage;

}
