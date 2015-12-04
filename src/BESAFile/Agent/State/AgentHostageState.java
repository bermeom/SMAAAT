/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESAFile.Agent.State.AgentState;
import BESAFile.Data.Vector3D;
import BESAFile.Model.DesiredGoal;
import BESAFile.World.Model.ModelFloor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class AgentHostageState extends AgentState{
    
    protected String agentFollow;
    protected String agentLeader;
    protected boolean following;
    protected Position postLeader;
    
    public AgentHostageState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, width, length, nFlooors);
        this.type=3;
        this.following=false;
        postLeader=null;
        this.speed=3;
    }

    public AgentHostageState(int xpos, int ypos, int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        super(xpos, ypos, idfloor, alias, direction, radius, height, width, length, nFlooors);
        this.type=3;
        this.following=false;
        postLeader=null;
        this.speed=3;
    }

    public String getAgentFollow() {
        return agentFollow;
    }

    public void setAgentFollow(String agentFollow) {
        this.agentFollow = agentFollow;
    }

    public String getAgentLeader() {
        return agentLeader;
    }

    public void setAgentLeader(String agentLeader) {
        this.agentLeader = agentLeader;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public Position getPostLeader() {
        return postLeader;
    }

    public void setPostLeader(Position postLeader) {
        this.postLeader = postLeader;
    }
    
    
    
    
}
