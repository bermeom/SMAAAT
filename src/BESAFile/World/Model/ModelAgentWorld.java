/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Model;

import BESAFile.Agent.State.Position;

/**
 *
 * @author berme_000
 */
public class ModelAgentWorld {
    private int id;
    private Position position;
    private int type;
    private String alias;
    
    public ModelAgentWorld(int xpos, int ypos, int idfloor, String alias,int type,int id) {
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
        this.id=id;
        this.type=type;
    }


    public ModelAgentWorld(int xpos, int ypos, int idfloor, String alias) {
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
    }
    
    public void setPos(int xpos, int ypos, int idfloor) {
        this.position.setIdfloor(idfloor);
        this.position.setXpos(xpos);
        this.position.setYpos(ypos);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    
}
