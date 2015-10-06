/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.World.Model.ModelEdifice;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author angel
 */
public class AgentState extends  StateBESA{
        
    protected ModelEdifice edifice;
    protected  int xpos;
    protected  int ypos;
    protected  int idfloor;
    protected  String alias;
    protected  Queue<Motion> possibleMotions;

    public AgentState(ModelEdifice edifice, int xpos, int ypos, int idfloor, String alias) {
        this.edifice = edifice;
        this.xpos = xpos;
        this.ypos = ypos;
        this.idfloor = idfloor;
        this.alias = alias;
        possibleMotions=new ArrayDeque<Motion>();
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

    public Queue<Motion> getPossibleMotions() {
        return possibleMotions;
    }

    public void setPossibleMotions(Queue<Motion> possibleMotions) {
        this.possibleMotions = possibleMotions;
    }
    
    
    
        
}
