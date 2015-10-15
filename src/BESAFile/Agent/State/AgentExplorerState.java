/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESAFile.Agent.State.AgentState;
import BESAFile.Data.Vector3D;

/**
 *
 * @author berme_000
 */
public class AgentExplorerState extends AgentState{
    
    
    public AgentExplorerState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, width, length, nFlooors);
        this.type=2;
        this.speed=5;
    }

    public AgentExplorerState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, height, width, length, nFlooors);
        this.type=2;
        this.speed=5;
    }

    
    
}
