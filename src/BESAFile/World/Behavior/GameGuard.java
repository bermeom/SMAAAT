/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.PeriodicGuardBESA;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelAgentWorld;
import BESAFile.World.Model.ModelFloor;
import BESAFile.World.State.WorldState;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author angel
 */
public class GameGuard extends PeriodicGuardBESA {

    @Override
    public void funcPeriodicExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            WorldState ws = (WorldState)this.getAgent().getState();
            ModelEdifice we=ws.getEdifice();//new ModelEdifice(ws.getEdifice().getWidth(),ws.getEdifice().getLength(),ws.getEdifice().getnFlooors());
            ModelFloor[] floors=ws.getEdifice().getFloors().clone();
            int i=1;
            char value;
            HashMap<String, ModelAgentWorld> hp=(HashMap)ws.getAgents();
            Set<String> keys=(Set<String>)hp.keySet();
            for(String key:keys){
                if (i>10){
                    break;
                }
                ModelAgentWorld wa=hp.get(key);
                we.addObject(wa.getIdfloor(), wa.getXpos(), wa.getYpos(), (char) ('0'+i));
                System.out.println(key);
                i++;
            }
            System.out.println(we);    
            we.setFloors(floors);
        } catch (Exception e) {
        
        }
        
    }
    
}
