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
public class ShootAgentDataJME extends DataBESA{
    
    private int reply_with;
    private int in_reply_to;
    protected  String alias;
    protected int type;
    protected Position position;
    protected Position target;

    public ShootAgentDataJME(int reply_with, int in_reply_to, String alias, int type, Position position, Position target) {
        this.reply_with = reply_with;
        this.in_reply_to = in_reply_to;
        this.alias = alias;
        this.type = type;
        this.position = position;
        this.target = target;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getTarget() {
        return target;
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    
    
    
}
