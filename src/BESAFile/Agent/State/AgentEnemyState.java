/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESAFile.Data.Vector3D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class AgentEnemyState extends AgentState{
    
    private List<String> hostages;
    
    public AgentEnemyState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, width, length, nFlooors);
        this.type=4;
        this.hostages=new ArrayList<String>();
    }

    public AgentEnemyState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, height, width, length, nFlooors);
        this.type=4;
        this.hostages=new ArrayList<String>();
    }
    
    public void addHostage(String nameHostage){
        hostages.add(nameHostage);
    }

    public List<String> getHostages() {
        return hostages;
    }

    public void setHostages(List<String> hostages) {
        this.hostages = hostages;
    }

    
    
    
}
