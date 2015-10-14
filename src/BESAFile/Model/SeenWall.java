/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Model;

import BESAFile.Data.Vector3D;

/**
 *
 * @author berme_000
 */
public class SeenWall extends SeenObject{
    
    protected  int xpos;
    protected  int ypos;
    protected  int idfloor;
    protected  char wall;

    
    public SeenWall(Vector3D position, String name, int type) {
        super(position, name, type);
        String []s=name.split("-");
        this.wall=s[0].charAt(0);
        this.idfloor=Integer.parseInt(s[1]);
        this.xpos=Integer.parseInt(s[2]);
        this.ypos=Integer.parseInt(s[3]);
    }

    @Override
    public String toString() {
        return "SeenWall{" + "xpos=" + xpos + ", ypos=" + ypos + ", idfloor=" + idfloor + ", wall=" + wall + '}';
    }
    
    
    
}
