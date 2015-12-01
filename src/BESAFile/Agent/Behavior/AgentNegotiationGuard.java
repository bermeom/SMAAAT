/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.NegotiationData;
import BESAFile.World.Behavior.UpdateGuardJME;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class AgentNegotiationGuard extends GuardBESA  {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        try {
            AgentState state = (AgentState) this.getAgent().getState();
            NegotiationData data = (NegotiationData ) ebesa.getData();
            if (state.getNegotiationData().isIsNull()){
                sendMessageNegotiation(data, state.getAlias());
                return;
            }
            NegotiationData nd=state.getNegotiationData(); 
            boolean ganador=true;
            if (nd.getBet()==data.getBet()){
                if (nd.getType()==data.getType()){
                    if (extractIDString(nd.getAlias())>extractIDString(data.getAlias())){
                        ganador=true;
                    }else{
                        ganador=false;
                    }
                }else if (nd.getType()>data.getType()){
                            ganador=true;
                        }else{
                            ganador=false;
                        }
            }else if (nd.getBet()>data.getBet()){
                        ganador=true;
                        }else{
                            ganador=false;
                        }
            //System.out.println("------------- Negotiation ->");
            //System.out.println("------------- Negotiation ->"+nd);
            //System.out.println("------------- Negotiation ->"+data);
            
            if (ganador){
                //System.out.println("------------- Winner ->"+nd.getAlias());
                
            }else{
                //System.out.println("------------- Winner ->"+data.getAlias());
                state.addGoal(new Position(state.getGoal().getXpos(), state.getGoal().getYpos(), state.getGoal().getIdfloor()), false, true,state.getEdifice().getPostGridFloor(state.getGoal()));
                //state.addGoal(data.getGoal(), false, true);
                System.out.println(state.getDesiredGoal());
            }
            state.setWinNegotiation(ganador);
            ActionDataAgent ad=new ActionDataAgent(data.getIn_reply_to(), data.getReply_with(),data.getAlias(), "ACK_NEGOTIATION");
            Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR NEGOTIATION xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
             Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
 
        }
    
    }
    
    private int extractIDString(String s){
        int id=0,k=1;
        char d;
        for(int i=s.length()-1;i>=0;i--){
            d=s.charAt(i);
            if(Character.isDigit(d)){
                id+=Integer.parseInt(d+"")*k;
                k*=10;
            }else{
                break;
            }        
        }
        return  id;
    }
    
    private void sendMessageNegotiation(NegotiationData actionData, String alias ) {
        boolean sw=false;
        //do{
            try {
                Agent.sendMessage(AgentNegotiationGuard.class,alias, actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    
    
    }
}
