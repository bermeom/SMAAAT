/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;

/**
 *
 * @author berme_000
 */
public class ActionDataWalkerNav extends DataBESA {
    private float tpf;
    private String action;

    public ActionDataWalkerNav(float tpf, String action) {
        this.tpf = tpf;
        this.action = action;
    }

    
    public float getTpf() {
        return tpf;
    }

    public void setTpf(float tpf) {
        this.tpf = tpf;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    
    
    
}
