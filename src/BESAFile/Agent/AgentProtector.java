/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.AgentBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.KernellAgentExceptionBESA;
import BESA.Kernell.Agent.StateBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Data.SubscribeDataJME;
import BESAFile.Data.Vector3D;
import BESAFile.World.Behavior.SubscribeGuardJME;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class AgentProtector extends AgentBESA{

    public AgentProtector(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernellAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        ReportBESA.info("SETUP AGENT -> " + getAlias());
        sendEventSubscribeJME();
    }

    @Override
    public void shutdownAgent() {
        ReportBESA.info("SHUTDOWN AGENT -> " + getAlias());
    
    }
    
    private void sendEventSubscribeJME() {
        AgentProtectorState state = (AgentProtectorState) this.state;
        SubscribeDataJME actionData = new SubscribeDataJME(state.getXpos(), state.getYpos(), state.getIdfloor(), this.getAlias(), state.getDirection(), 1, state.getRadius(), state.getHeight());
        EventBESA event = new EventBESA(SubscribeGuardJME.class.getName(), actionData);
        
        try {
            AgHandlerBESA ah = AdmBESA.getInstance().getHandlerByAlias(Const.World);
            ah.sendEvent(event);
        } catch (ExceptionBESA ex) {
            Logger.getLogger(AgentProtector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    
}
