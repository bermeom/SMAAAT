/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;

/**
 *
 * @author berme_000
 */
public class AddAgentDataJME extends SubscribeDataJME{

    protected int reply_with;
    protected int in_reply_to;
    
    public AddAgentDataJME(int reply_with,int in_reply_to,int xpos, int ypos, int idfloor, String alias, Vector3D direction, int type, double radius) {
        super(xpos, ypos, idfloor, alias, direction, type, radius);
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
    }

    public AddAgentDataJME(int reply_with,int in_reply_to,int xpos, int ypos, int idfloor, String alias, Vector3D direction, int type, double radius, double height) {
        super(xpos, ypos, idfloor, alias, direction, type, radius, height);
        this.reply_with=reply_with;
        this.in_reply_to=in_reply_to;
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
    
    
}
