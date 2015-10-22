/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World;

import BESA.Kernell.Agent.AgentBESA;
import BESA.Kernell.Agent.KernellAgentExceptionBESA;
import BESA.Kernell.Agent.StateBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Log.ReportBESA;

/**
 *
 * @author berme_000
 */
public class WorldAgentJME extends AgentBESA{

    public WorldAgentJME(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernellAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ReportBESA.info("SETUP AGENT -> " + getAlias());
    }

    @Override
    public void shutdownAgent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ReportBESA.info("SHUTDOWN AGENT -> " + getAlias());
    }
    
}