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

    public Position(int xpos, int ypos, int idfloor) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
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

    @Override
    public String toString() {
        return "Position{" + "xpos=" + xpos + ", ypos=" + ypos + ", idfloor=" + idfloor + '}';
    }
    
    
    
}
