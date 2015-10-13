/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.PeriodicGuardBESA;
import BESAFile.World.State.WorldStateJME;

/**
 *
 * @author berme_000
 */
public class SimulationGuardJME  extends PeriodicGuardBESA {

    @Override
    public void funcPeriodicExecGuard(EventBESA ebesa) {
        try {
                WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
                
        } catch (Exception e) {

        }

    }
    
}
