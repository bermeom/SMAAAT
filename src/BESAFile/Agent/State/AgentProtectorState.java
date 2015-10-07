/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Agent.State.AgentState;
import BESAFile.World.Model.ModelEdifice;
import simulation.Agent.GuardianAgent;

/**
 *
 * @author berme_000
 */
public class AgentProtectorState  extends AgentState {
    protected GuardianAgent ga;

    public AgentProtectorState(GuardianAgent ga, ModelEdifice edifice, int xpos, int ypos, int idfloor, String alias) {
        super(edifice, xpos, ypos, idfloor, alias);
        this.ga = ga;
    }

    public GuardianAgent getGa() {
        return ga;
    }

    public void setGa(GuardianAgent ga) {
        this.ga = ga;
    }
    
    
    
    
}
