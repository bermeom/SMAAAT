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
public class NegotiationData extends DataBESA{
    
    private int bet;
    private int type;
    private String alias;
    //private String action;
    private Position goal;
    private int reply_with;
    private int in_reply_to;
    private boolean isNull;
    

    public NegotiationData() {
        this.isNull=true;
    }

    public NegotiationData(int reply_with, int in_reply_to, int type, String alias,Position goal,int bet) {
        this.isNull=false;
        this.bet = bet;
        this.type = type;
        this.alias = alias;
        this.reply_with = reply_with;
        this.in_reply_to = in_reply_to;
        this.goal=goal;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
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

    public boolean isIsNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public Position getGoal() {
        return goal;
    }

    public void setGoal(Position goal) {
        this.goal = goal;
    }
    
    

    @Override
    public String toString() {
        return "NegotiationData{" + "bet=" + bet + ", type=" + type + ", alias=" + alias + ", reply_with=" + reply_with + ", in_reply_to=" + in_reply_to + ", isNull=" + isNull + '}';
    }
    
    
    
    
}
