/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent;

import BESA.Kernell.Agent.AgentBESA;
import BESA.Kernell.Agent.KernellAgentExceptionBESA;
import BESA.Kernell.Agent.StateBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Log.ReportBESA;

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
        
    }

    @Override
    public void shutdownAgent() {
        ReportBESA.info("SHUTDOWN AGENT -> " + getAlias());
    
    }

    
}
