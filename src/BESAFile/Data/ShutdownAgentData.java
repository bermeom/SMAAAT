/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;
import BESAFile.Agent.State.Position;

/**
 *
 * @author berme_000
 */
public class ShutdownAgentData extends DataBESA{
    
    protected String alias;
    protected Position position;

    public ShutdownAgentData(String alias, Position position) {
        this.alias = alias;
        this.position = position;
    }
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
}
