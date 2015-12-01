/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelAgentWorld;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author berme_000
 */
public class WorldState extends StateBESA{
    
    private ModelEdifice edifice;
    private Map<String,ModelAgentWorld> agents;

    public WorldState(ModelEdifice edifice) {
        this.edifice = edifice;
        agents=new HashMap<String,ModelAgentWorld>();
    }
    
    public void  addAgent(int xpos, int ypos, int idfloor, String alias){
        //agents.put(alias,new ModelAgentWorld(xpos, ypos, idfloor, alias));
    }
    
    public void  removeAgent(String alias){
        agents.remove(alias);
    }
    
    public boolean moveAgent(int xpos, int ypos, int idfloor,String alias){
        if(edifice.getPostGridFloor(idfloor, xpos, ypos)=='0'){
            if(findAgent(xpos, ypos, idfloor)){
                return false;
            }
            edifice.setPostGridFloor(agents.get(alias).getIdfloor(),agents.get(alias).getXpos(), agents.get(alias).getYpos(),'0');
            agents.get(alias).setPos(xpos, ypos, idfloor);
            return true;
        }
        return false;
    }
    
    private boolean findAgent(int xpos,int ypos,int idfloor){
        for(String key:(Set<String>)agents.keySet()){
            ModelAgentWorld wa=agents.get(key);
            if(wa.getXpos()==xpos&&wa.getYpos()==ypos&&wa.getIdfloor()==idfloor){
                return true;
            }
        }
            return false;
    
    }
    
    public ModelEdifice getEdifice() {
        return edifice;
    }

    public void setEdifice(ModelEdifice edifice) {
        this.edifice = edifice;
    }

    public Map<String, ModelAgentWorld> getAgents() {
        return agents;
    }

    public void setAgents(Map<String, ModelAgentWorld> agents) {
        this.agents = agents;
    }
    
    
    
    
}
