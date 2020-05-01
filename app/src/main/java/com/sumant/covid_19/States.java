package com.sumant.covid_19;

public class States {
    String states, confirm , active, recover, death;

    public States(String states, String confirm, String active, String recover, String death) {
        this.states = states;
        this.confirm = confirm;
        this.active = active;
        this.recover = recover;
        this.death = death;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecover() {
        return recover;
    }

    public void setRecover(String recover) {
        this.recover = recover;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}
