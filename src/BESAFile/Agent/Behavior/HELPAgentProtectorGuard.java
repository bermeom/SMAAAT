/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Log.ReportBESA;
import BESAFile.Data.ActionDataAgent;
import simulation.utils.Const;

/**
 *
 * @author sala_a
 */
public class HELPAgentProtectorGuard  extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        try {
               ActionDataAgent data = (ActionDataAgent) ebesa.getData();
               System.out.println(">>>>>>>>>>>> HELP!!!!!! "+data.getAlias());
           } catch (Exception e) {
           }
    }
    
}