/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Data.Vector3D;
import BESAFile.World.Model.ModelEdifice;
import java.util.Queue;

/**
 *
 * @author berme_000
 */
public class AgentState extends  StateBESA{
        
    protected ModelEdifice edifice;
    protected  int xpos;
    protected  int ypos;
    protected  int idfloor;
    protected  String alias;
    protected Vector3D direction;
    protected double radius;
    protected double height;
    protected float sightRange;
    protected  Queue<Vector3D> possibleMotions;
    protected int type;
    protected double speed;
    

    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor=idfloor;
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = radius*2;
        this.sightRange = 3f;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors);
        this.speed=3;
    }
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor=idfloor;
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = height;
        this.sightRange = 3f;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors);
        this.speed=3;
    }

    public ModelEdifice getEdifice() {
        return edifice;
    }

    public void setEdifice(ModelEdifice edifice) {
        this.edifice = edifice;
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

    public Queue<Vector3D> getPossibleMotions() {
        return possibleMotions;
    }

    public void setPossibleMotions(Queue<Vector3D> possibleMotions) {
        this.possibleMotions = possibleMotions;
    }

    public float getSightRange() {
        return sightRange;
    }

    public void setSightRange(float sightRange) {
        this.sightRange = sightRange;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void setModelEdiffice(int idFloor,int i,int j,char value){
        this.edifice.setPostGridFloor(idFloor, i, j, value);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    
}
