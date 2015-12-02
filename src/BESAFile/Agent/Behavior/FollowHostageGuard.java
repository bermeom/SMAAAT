/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.State.AgentHostageState;
import BESAFile.Data.FolowingData;

/**
 *
 * @author berme_000
 */
public class FollowHostageGuard extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
          try {
                
            AgentHostageState state = (AgentHostageState) this.getAgent().getState();
            if(state.getLife()<=0){
                return;
            }   
            FolowingData data = (FolowingData) ebesa.getData();
            if(!state.isFollowing()){
                state.setAgentFollow(data.getAgentFollow());
                state.setAgentLeader(data.getAgentLeader());
                state.setFollowing(true);
                System.out.println(" Leader = "+state.getAgentLeader());
                System.out.println(" Follow = "+state.getAgentFollow());
            }else{
                FolowingData fd=new FolowingData(data.getIn_reply_to(),data.getReply_with(), state.getType(), state.getAlias(), state.getPosition(), "NACK");
                Agent.sendMessage(HELPAgentProtectorGuard.class, data.getAlias(), fd);
            }
            } catch (Exception e) {
           
            }
    
    }
    
}
