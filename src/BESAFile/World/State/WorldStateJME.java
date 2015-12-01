/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
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
    private Map<String,Integer> mapIDAgent;
    private List<PositionController> agents;
    protected ModelEdifice mEdifice;
    protected SmaaatApp app;
    private List<ModelAgentWorld> listAgents;
    private int idFloor;
    public WorldStateJME(SmaaatApp app,int idFloor) {
        this.app=app;
        this.idFloor=idFloor;
        agents=new ArrayList< PositionController>();
        this.mEdifice=new ModelEdifice(app.getmEdifice().getWidth(), app.getmEdifice().getLength(), 1, false);
        this.mEdifice.setFloor(0,app.getmEdifice().getFloor(idFloor));
        this.consecutiveAgent=1;
        this.listAgents=new ArrayList<ModelAgentWorld>();
        this.mapIDAgent=new HashMap<String, Integer>();
        
    }

    public int getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(int idFloor) {
        this.idFloor = idFloor;
    }

    public List<PositionController> getAgents() {
        return agents;
    }

    public void setAgents(List<PositionController> agents) {
        this.agents = agents;
    }

    
    public SmaaatApp getApp() {
        return app;
    }

    public void setApp(SmaaatApp app) {
        this.app = app;
    }
    
    public void addAgent(String agent,PositionController pc,SubscribeDataJME data){
        
        if (mapIDAgent.get(agent)!=null){
           int id=mapIDAgent.get(agent);
           //PositionController pcOld=agents.get(id-1);
            agents.set(id-1, pc);
            this.listAgents.get(id-1).setPos(data.getXpos(), data.getYpos(), data.getIdfloor());
            this.listAgents.get(id-1).setIdGridP(mEdifice.getPostGridFloor(0, data.getXpos(), data.getYpos()));
            mEdifice.setPostGridFloor(0, data.getXpos(), data.getYpos(), id);
        }else{
            agents.add(pc);
            ModelAgentWorld maw=new ModelAgentWorld(data.getXpos(), data.getYpos(), data.getIdfloor(), data.getAlias(),data.getType(), consecutiveAgent,mEdifice.getPostGridFloor(0, data.getXpos(), data.getYpos()));
            this.listAgents.add(maw);
            //mEdifice.setPostGridFloor(data.getIdfloor(), data.getXpos(), data.getYpos(), consecutiveAgent);
            mEdifice.setPostGridFloor(0, data.getXpos(), data.getYpos(), consecutiveAgent);
            mapIDAgent.put(agent,consecutiveAgent);
            consecutiveAgent++;
        }
    }
    
    public boolean moveAgent(Position p,Motion m,int idAgent){
        if (this.listAgents.get(idAgent).getPosition().isEquals(p)/*&&(this.agents.get(idAgent).isValidationPosition()||!this.agents.get(idAgent).isEnabledPS())/**/){
            this.listAgents.get(idAgent).setPos(m.getXpos(), m.getYpos(), m.getIdfloor());
            this.listAgents.get(idAgent).setIdGridM(mEdifice.getPostGridFloor(0, m.getXpos(), m.getYpos()));
            return true;
        }
        return false;
    }
    
    public int getIdGridPAgent(int idAgent){
           return this.listAgents.get(idAgent).getIdGridP();
    }

    public int getIdGridMAgent(int idAgent){
           return this.listAgents.get(idAgent).getIdGridM();
    }
    
    public void setIdGridPAgent(int idAgent, int idGrid){
           this.listAgents.get(idAgent).setIdGridP(idGrid);
    }

    public void setIdGridMAgent(int idAgent, int idGrid){
           this.listAgents.get(idAgent).setIdGridM(idGrid);
    }

    
    public PositionController getAgentController(int idAgent){
        return agents.get(idAgent);
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
