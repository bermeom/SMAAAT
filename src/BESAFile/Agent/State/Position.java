/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

/**
 *
 * @author angel
 */
public class Position {
    protected  int xpos;
    protected  int ypos;
    protected  int idfloor;
    protected  boolean  isNull;

    public Position() {
        this.isNull=true;
    }
     

    public Position(Motion m) {
        this.xpos = m.getXpos();
        this.ypos = m.getYpos();
        this.idfloor = m.getIdfloor();
        this.isNull=false;
    }
    

    public Position(int xpos, int ypos, int idfloor) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.isNull=false;
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
    
    public boolean isEquals(Position p){
        return p.getIdfloor()==this.idfloor && p.getXpos()== this.xpos && p.getYpos()==this.ypos;
    }

    public boolean isEquals(Motion p){
        return p.getIdfloor()==this.idfloor && p.getXpos()== this.xpos && p.getYpos()==this.ypos;
    }

    public boolean isIsNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }
    
    public double euclideanDistance(Position p){
        return Math.sqrt((double)(this.xpos-p.getXpos())*(this.xpos-p.getXpos())+(double)(this.ypos-p.getYpos())-(this.ypos-p.getYpos()));
    }
    
    public double euclideanDistance(Motion p){
        return Math.sqrt((double)(this.xpos-p.getXpos())*(this.xpos-p.getXpos())+(double)(this.ypos-p.getYpos())-(this.ypos-p.getYpos()));
    }
    
    @Override
    public String toString() {
        return "Position{" + "xpos=" + xpos + ", ypos=" + ypos + ", idfloor=" + idfloor + '}';
    }
    
    
    
}
