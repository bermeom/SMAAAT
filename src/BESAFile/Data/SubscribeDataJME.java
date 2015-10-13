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
public class SubscribeDataJME extends DataBESA {
    private int xpos;
    private int ypos;
    private int idfloor;
    private int type;
    private String alias;
    private Vector3D direction;
    private double radius;
    private double height;
    
    public SubscribeDataJME(int xpos, int ypos, int idfloor, String alias, Vector3D direction,int type,double radius) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.type=type;
        this.alias = alias;
        this.direction = direction;
        this.radius=radius;
        this.height=2*radius;
    
    }

    public SubscribeDataJME(int xpos, int ypos, int idfloor, String alias, Vector3D direction,int type,double radius,double height) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.type=type;
        this.alias = alias;
        this.direction = direction;
        this.radius=radius;
        this.height=height;
    
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

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
    
    
}
