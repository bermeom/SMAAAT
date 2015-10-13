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
import simulation.SmaaatApp;

/**
 *
 * @author berme_000
 */
public class WorldStateJME extends StateBESA{
    
    private List<String> agents;
    protected SmaaatApp app;

    public WorldStateJME(SmaaatApp app) {
        this.app=app;
        agents=new ArrayList<String>();
    }

    public List<String> getAgents() {
        return agents;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }

    public SmaaatApp getApp() {
        return app;
    }

    public void setApp(SmaaatApp app) {
        this.app = app;
    }
    
    public void addAgent(String agent){
        agents.add(agent);
    }
    

    
}
