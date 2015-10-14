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
    private int type;
    private String alias;
    private String action;
    private Vector3D position;
    private Vector3D direction;
    protected float speed = 3;
    
    
    
    public ActionData(int xpos, int ypos, int idfloor, String alias, String action) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.alias = alias;
        this.action = action;
        this.position = new  Vector3D();
        this.speed=speed;
    }
    
    
    public ActionData(int type,int xpos, int ypos, int idfloor, String alias, String action, Vector3D direction,float speed) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.alias = alias;
        this.action = action;
        this.direction = direction;
        this.speed=speed;
        this.type=type;
 
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

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

   

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
    
}
