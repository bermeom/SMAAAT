/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.FolowingData;
import java.util.List;
import simulation.utils.Const;

/**
 *
 * @author sala_a
 */
public class HELPAgentProtectorGuard  extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        try {
                
            AgentProtectorState state = (AgentProtectorState) this.getAgent().getState();
            if(state.getLife()<=0){
                return;
            }   
            FolowingData data = (FolowingData) ebesa.getData();
            System.out.println(">>>>>>>>>>>> HELP!!!!!! "+data.getAlias());
           
            
            if (data.getAction().equals("HELP")&&state.getHostages().size()<=2){
                boolean sw=false;
                for (String s : state.getHostages()) {
                    if(s.equals(data.getAlias())){
                        sw=true;
                    }
                }
                if(sw){
                    return;
                }
                state.addHostage(data.getAlias());
                String agentFollowing;
                String agentLeader=state.getAlias();
                
                if(state.getHostages().size()==1){
                    agentFollowing=state.getAlias();
                }else{
                    agentFollowing=state.getHostages().get(0);
                }
                FolowingData fd=new FolowingData(data.getIn_reply_to(),data.getReply_with(), agentFollowing, agentLeader,"REPLAY");
                Agent.sendMessage(FollowHostageGuard.class, data.getAlias(), fd);
            }else if(data.getAction().equals("NACK")){
                List<String> h=state.getHostages();
                int k=-1;
                for(int i=0;i<h.size();i++){
                    if(h.get(i).equals(data.getAlias())){
                        k=i;
                    }
                }    
                if(k!=-1){
                    state.getHostages().remove(k);
                }
                
                }
            } catch (Exception e) {
           
            }
    }
    
}
