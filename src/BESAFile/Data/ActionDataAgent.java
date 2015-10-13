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
public class ActionDataAgent extends DataBESA {
    
    private float sightRange;
    protected double radius;
    protected double height;
    private String action;
    private String alias;
    private int type;
    protected  int idfloor;

    
    public ActionDataAgent(int type,String alias) {
        this.alias = alias;
        this.type = type;
    }
    
    public ActionDataAgent(int idfloor,float sightRange, double radius, double height, String alias, String action) {
        this.idfloor=idfloor;
        this.sightRange = sightRange;
        this.radius = radius;
        this.height = height;
        this.action = action;
        this.alias = alias;
    }
    
    public ActionDataAgent(String action) {
        this.sightRange = sightRange;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getSightRange() {
        return sightRange;
    }

    public void setSightRange(float sightRange) {
        this.sightRange = sightRange;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdfloor() {
        return idfloor;
    }

    public void setIdfloor(int idfloor) {
        this.idfloor = idfloor;
    }
    
    
    
    
    
    
    
    
}
