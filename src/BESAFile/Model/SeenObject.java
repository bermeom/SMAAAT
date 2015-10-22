/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Model;

import BESAFile.Agent.State.Position;


/**
 *
 * @author berme_000
 */
public class SeenObject {
    protected Position position;
    protected  String name;
    protected  int type;

    public SeenObject(Position position, String name, int type) {
        this.position = position;
        this.name = name;
        this.type = type;
    }

    public Position getPosition() {
           return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return   " name= " + name +" position= " + position + " type=" + type ;
    }
    
}
