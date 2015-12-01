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
public class ChangeFloorData extends DataBESA{
    private int reply_with;
    private int in_reply_to;
    private int typeChange;
    protected Position position;
    protected  String alias;
    protected int type;
    public ChangeFloorData(int reply_with, int in_reply_to, int typeChange,int type, Position position, String alias) {
        this.reply_with = reply_with;
        this.in_reply_to = in_reply_to;
        this.typeChange = typeChange;
        this.position = position;
        this.alias=alias;
        this.type=type;
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

    public int getTypeChange() {
        return typeChange;
    }

    public void setTypeChange(int typeChange) {
        this.typeChange = typeChange;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    
    
    
}
