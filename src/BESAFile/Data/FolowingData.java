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
public class FolowingData extends DataBESA{
   
    private int reply_with;
    private int in_reply_to;
    private int type;
    private String alias;
    private String action;
    protected Position position;
    protected String agentFollow;
    protected String agentLeader;

    public FolowingData(int reply_with, int in_reply_to, int type, String alias, Position position,String action) {
        this.reply_with = reply_with;
        this.in_reply_to = in_reply_to;
        this.type = type;
        this.alias = alias;
        this.position = position;
        this.action=action;
    }

    public FolowingData(int reply_with, int in_reply_to, String agentFollow, String agentLeader,String action) {
        this.reply_with = reply_with;
        this.in_reply_to = in_reply_to;
        this.agentFollow = agentFollow;
        this.agentLeader = agentLeader;
        this.action=action;
    }
    
    public int getReply_with() {
        return reply_with;
    }

    public void setReply_with(int reply_with) {
        this.reply_with = reply_with;
    }

    public int getIn_reply_to() {
        return in_reply_to;
    }

    public void setIn_reply_to(int in_reply_to) {
        this.in_reply_to = in_reply_to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    
    
    
}
