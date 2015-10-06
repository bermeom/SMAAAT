/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Data.SubscribeData;
import BESAFile.World.State.WorldState;

/**
 *
 * @author angel
 */
public class SubscribeGuard extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            SubscribeData data = (SubscribeData)ebesa.getData();
            WorldState ws = (WorldState)this.getAgent().getState();
            ws.addAgent(data.getXpos(), data.getYpos(), data.getIdfloor(), data.getAlias());
        } catch (Exception e) {
        
        
        }
    
    }
    
}
