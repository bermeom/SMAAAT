/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent;

import BESAFile.Agent.State.AgentState;
import BESA.ExceptionBESA;
import BESA.Kernell.Agent.AgentBESA;
import BESA.Kernell.Agent.Event.DataBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.KernellAgentExceptionBESA;
import BESA.Kernell.Agent.StateBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Data.SubscribeData;
import BESAFile.World.Behavior.SubscribeGuard;
import java.util.Random;

/**
 *
 * @author angel
 */
public class Agent extends AgentBESA{

    public Agent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernellAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ReportBESA.info("SETUP AGENT -> " + getAlias());
        AgentState as = (AgentState)this.getState();
        SubscribeData data =new SubscribeData(as.getXpos(), as.getYpos(), as.getIdfloor(), as.getAlias());
        EventBESA event = new EventBESA(SubscribeGuard.class.getName(), data);
        AgHandlerBESA ah;
        
        try {
            ah = this.getAdmLocal().getHandlerByAlias("WORLD");
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
        
        
    }

    @Override
    public void shutdownAgent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ReportBESA.info("SHUTDOWN AGENT -> " + getAlias());
    
    }
    
}
