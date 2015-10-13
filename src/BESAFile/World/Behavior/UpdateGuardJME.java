/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.State.WorldState;
import BESAFile.World.State.WorldStateJME;

/**
 *
 * @author berme_000
 */
public class UpdateGuardJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            WorldStateJME state = (WorldStateJME)this.getAgent().getState();
            switch (data.getAction()) {
                case "move":
                            System.out.println("-------------------Move World:D--------- ");
                            //moveAgent(data, state);
                            break;
                    
            }
            
        } catch (Exception e) {
        }
    
    }
    
    public void moveAgent(ActionData data,WorldState state){
        ActionData actionData=new ActionData(data.getXpos(), data.getYpos(), data.getIdfloor(),data.getAlias(), "NAKMove");
        if (state.moveAgent(data.getXpos(),data.getYpos(),data.getIdfloor(),data.getAlias())){
            actionData.setAction("ACKMove");
        }
        EventBESA event = new EventBESA(AgentMoveGuard.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(data.getAlias());
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }    
    
    }
}
