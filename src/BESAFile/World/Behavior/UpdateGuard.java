/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Data.ActionData;
import BESAFile.World.State.WorldState;

/**
 *
 * @author angel
 */
public class UpdateGuard extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            ActionData data = (ActionData) ebesa.getData();
            WorldState state = (WorldState)this.getAgent().getState();
            switch (data.getAction()) {
                case "move":
                            //System.out.println("-------------------Move World:D--------- "+data.getXpos()+" "+data.getYpos());
                            moveAgent(data, state);
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
