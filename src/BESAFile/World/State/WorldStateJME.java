/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelAgentWorld;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import simulation.SmaaatApp;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class WorldStateJME extends StateBESA{
    
    private Map<String,Node> agents;
    protected SmaaatApp app;

    public WorldStateJME(SmaaatApp app) {
        this.app=app;
        agents=new HashMap<String, Node>();
    }

    public Map<String, Node> getAgents() {
        return agents;
    }

    public void setAgents(Map<String, Node> agents) {
        this.agents = agents;
    }

    public SmaaatApp getApp() {
        return app;
    }

    public void setApp(SmaaatApp app) {
        this.app = app;
    }
    
    public void addAgent(String agent,Node node){
        agents.put(agent,node);
    }
    
    public Node getNodeAgent(String agent){
        return agents.get(agent);
    }
    

    
    

    
}
