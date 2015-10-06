/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Model;

/**
 *
 * @author angel
 */
public class ModelObjectWorld {
    
    private int xpos;
    private int ypos;
    private char object;

    public ModelObjectWorld(int xpos, int ypos, char object) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.object = object;
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

    public char getObject() {
        return object;
    }

    public void setObject(char object) {
        this.object = object;
    }
    
    

    
}
