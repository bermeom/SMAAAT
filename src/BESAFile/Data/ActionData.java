/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;

/**
 *
 * @author berme_000
 */
public class ActionData extends DataBESA{
    private int type;
    private String alias;
    private String action;
    protected float speed;
    protected double radius;
    protected double height;
    protected float sightRange;
    protected Motion motion;
    protected Position position;
    //
    private int reply_with;
    private int in_reply_to;
    private int id;

    public ActionData() {
    }
    
    public ActionData(int reply_with,int in_reply_to,int type, String alias, String action) {
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
        this.type = type;
        this.alias = alias;
        this.action = action;
    }

    public ActionData(int reply_with,int in_reply_to,int type, String alias, float speed, Motion motion, Position position, String action) {
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
        this.type = type;
        this.alias = alias;
        this.action = action;
        this.speed = speed;
        this.motion = motion;
        this.position = position;
    }
    
    
    
    
    public ActionData(int reply_with,int in_reply_to,int type,String alias,Vector3D direction,Position position, float speed,double radius,double height) {
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
        this.alias = alias;
        this.position = position;
        this.speed=speed;
        this.radius=radius;
        this.height=height;
        this.type=type;
        
    }
    
    
    public ActionData(int reply_with,int in_reply_to,int xpos, int ypos, int idfloor, String alias, String action) {
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
        this.action = action;
        this.speed=speed;
    }
    
    
    public ActionData(int reply_with,int in_reply_to,int type,int xpos, int ypos, int idfloor, String alias, String action,float speed) {
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
        this.action = action;
        this.speed=speed;
        this.type=type;
 
    }
    
    public int getXpos() {
        return this.position.getXpos();
    }

    public void setXpos(int xpos) {
        this.position.setXpos(xpos);
    }

    public int getYpos() {
        return this.position.getYpos();
    }

    public void setYpos(int ypos) {
        this.position.setYpos(ypos);
    }

    public int getIdfloor() {
        return this.position.getIdfloor();
    }

    public void setIdfloor(int idfloor) {
        this.position.setIdfloor(idfloor);
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

    
    public Motion getMotion() {
        return motion;
    }

    public void setMotion(Motion motion) {
        this.motion = motion;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public float getSightRange() {
        return sightRange;
    }

    public void setSightRange(float sightRange) {
        this.sightRange = sightRange;
    }

    public int getReply_with() {
        return reply_with;
    }

    public void setReply_with(int reply_with) {
        this.reply_with = reply_with;
    }

    public int getIn_reply_to() {
        return in_reply_to;
    }

    public void setIn_reply_to(int in_reply_to) {
        this.in_reply_to = in_reply_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
}
