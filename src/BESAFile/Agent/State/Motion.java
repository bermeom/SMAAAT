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
public class Motion {
    protected  int xpos;
    protected  int ypos;
    protected  int idfloor;

    public Motion(int xpos, int ypos, int idfloor) {
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
    
    
}
