/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.State.AgentState;
import BESAFile.Data.ShutdownAgentData;
import BESAFile.World.Behavior.ShutdownAgentGuardJME;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class DecreaseLifeGuard  extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
         AgentState state = (AgentState) this.getAgent().getState();
         state.decreaseLife();
         if(state.getLife()<=0){
             ShutdownAgentData data=new ShutdownAgentData(state.getAlias(), state.getPosition());
             Agent.sendMessage(ShutdownAgentGuardJME.class, Const.World+state.getIdfloor(), data);
            }
    
    }
    
}
