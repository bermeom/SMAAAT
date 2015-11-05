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
import BESAFile.Agent.Behavior.SubscribeResponseGuard;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.Model.ModelAgentWorld;
import BESAFile.World.State.WorldStateJME;

/**
 *
 * @author berme_000
 */
public class SimulationStartJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
         try {
            WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
            for(ModelAgentWorld maw:ws.getListAgents()){
                answer(true, maw.getAlias());
            }
            //ws.getApp().getBulletAppState().setDebugEnabled(false);
        } catch (Exception e) {
            ReportBESA.error(e);
        }
    }
    
    public void answer(boolean ack,String alias){
        int reply_with=1;
        int in_reply_to=-1;
        ActionDataAgent actionData=new ActionDataAgent(reply_with,in_reply_to,"NAK");
        if (ack){
            actionData.setAction("ACK");
        }
        boolean sw=false;
        EventBESA event = new EventBESA(SubscribeResponseGuard.class.getName(), actionData);
        AgHandlerBESA ah;
        do{
            try {
                ah = getAgent().getAdmLocal().getHandlerByAlias(alias);
                ah.sendEvent(event);
                sw=true;
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
                sw=false;
            }  
        }while(!sw);
    
    }
    
}
