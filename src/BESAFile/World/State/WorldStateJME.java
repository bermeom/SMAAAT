/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Data.SubscribeDataJME;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelAgentWorld;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import simulation.Controls.PositionController;
import simulation.SmaaatApp;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class WorldStateJME extends StateBESA{
    
    private int consecutiveAgent;
    private Map<String,PositionController> agents;
    protected ModelEdifice mEdifice;
    protected SmaaatApp app;
    private List<ModelAgentWorld> listAgents;

    public WorldStateJME(SmaaatApp app) {
        this.app=app;
        agents=new HashMap<String, PositionController>();
        this.mEdifice=app.getmEdifice();
        this.consecutiveAgent=1;
        this.listAgents=new ArrayList<ModelAgentWorld>();
    }

    public Map<String, PositionController> getAgents() {
        return agents;
    }

    public void setAgents(Map<String, PositionController> agents) {
        this.agents = agents;
    }

    public SmaaatApp getApp() {
        return app;
    }

    public void setApp(SmaaatApp app) {
        this.app = app;
    }
    
    public void addAgent(String agent,PositionController pc,SubscribeDataJME data){
        agents.put(agent,pc);
        ModelAgentWorld maw=new ModelAgentWorld(data.getXpos(), data.getYpos(), data.getIdfloor(), data.getAlias(), consecutiveAgent,data.getType());
        this.listAgents.add(maw);
        
        mEdifice.setPostGridFloor(data.getIdfloor(), data.getXpos(), data.getYpos(), consecutiveAgent);
        consecutiveAgent++;
       
    }
    
    public PositionController getNodeAgent(String agent){
        return agents.get(agent);
    }

    public int getConsecutiveAgent() {
        return consecutiveAgent;
    }

    public void setConsecutiveAgent(int consecutiveAgent) {
        this.consecutiveAgent = consecutiveAgent;
    }

    public ModelEdifice getmEdifice() {
        return mEdifice;
    }

    public void setmEdifice(ModelEdifice mEdifice) {
        this.mEdifice = mEdifice;
    }

    public List<ModelAgentWorld> getListAgents() {
        return listAgents;
    }

    public void setListAgents(List<ModelAgentWorld> listAgents) {
        this.listAgents = listAgents;
    }
    
    
    

    
    

    
}
