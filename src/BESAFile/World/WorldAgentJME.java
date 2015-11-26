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
import BESAFile.World.State.WorldStateJME;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.SmaaatApp;

/**
 *
 * @author berme_000
 */
public class WorldAgentJME extends AgentBESA{

    protected SmaaatApp app;
    
    public WorldAgentJME(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernellAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
        WorldStateJME ws=(WorldStateJME)state;
        this.app=ws.getApp();
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
    
    //******************BEHAVIORS******************
    public <T> T execAsyncInWorldThread(Callable<T> func) {
        Future<T> future = app.enqueue(func);
        T result = null;
        try {
            result = future.get();
        } catch (Exception ex) {
            Logger.getLogger(WorldAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
