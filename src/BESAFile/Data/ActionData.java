/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;

/**
 *
 * @author berme_000
 */
public class ActionData extends DataBESA{
    private int xpos;
    private int ypos;
    private int idfloor;
    private String alias;
    private String action;

    public ActionData(int xpos, int ypos, int idfloor, String alias, String action) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.alias = alias;
        this.action = action;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getIdfloor() {
        return idfloor;
    }

    public void setIdfloor(int idfloor) {
        this.idfloor = idfloor;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ActionData{" + "xpos=" + xpos + ", ypos=" + ypos + ", idfloor=" + idfloor + ", alias=" + alias + ", action=" + action + '}';
    }
    
    
    
}
