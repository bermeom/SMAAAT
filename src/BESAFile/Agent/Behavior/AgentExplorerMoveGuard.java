/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Agent.State.AgentExplorerState;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.AgentStateTest;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.NegotiationData;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import BESAFile.Model.SeenWall;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import BESAFile.World.Behavior.UpdateGuardJME;
import BESAFile.World.Model.ModelEdifice;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.utils.Const;
import simulation.utils.Utils;

/**
 *
 * @author berme_000
 */
public class AgentExplorerMoveGuard extends GuardBESA  {
    
    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            AgentState state = (AgentState) this.getAgent().getState();
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            switch (data.getAction()) {
                case "move":
                    //ReportBESA.info("-------------------Move:D--------- "+((AgentState) this.getAgent().getState()).getAlias());
                    moveAgent(data.getReply_with(),data.getIn_reply_to(),data.getMotion());

                    break;
                case "NACK":
                    nack(data,state);
                    break;
                case "ACK":
                     ack(data,state);
                     break;
                case("ACK_SENSOR"):
                    ackSensor(data);
                    break;
                case("ACK_NEGOTIATION"):
                    ackNegotiation(data,state);
                    break;

            }
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
 
        }
    }

    public void moveAgent(int reply_with,int in_reply_to,Motion motion) {
        try {
            AgentState state = (AgentState) this.getAgent().getState();
            ActionData ad = new ActionData(reply_with,in_reply_to,state.getType(), state.getAlias(),(float)state.getSpeed(),motion,state.getPosition(), "move");
            Agent.sendMessage(UpdateGuardJME.class,Const.World, ad);
        } catch (Exception e) {
               System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        }
        

    }

    public boolean intervalValidation(int n, int limit) {
        return n >= 0 && n < limit;
    }
    
    private boolean compareDistance(double a, double b,double delta){
        return Math.abs(a-b)<=delta;
    } 
    
    private Vector3D vectorDirection(Vector3D f,Vector3D a){
            return (new Vector3D((a.getX()-f.getX()), a.getY()-f.getY(), a.getZ()-f.getZ())).normalize1();
        
    }
    
    private int[][] border(int [][]mat, Position p, int width,int length ,int tam){
        /*if(p.getYpos()+1>=width){
            for(int i=0;i<tam;i++){
                mat[i][1+(int)(tam/2)]=false;
            }
        }
        if(p.getYpos()-1<0){
            for(int i=0;i<tam;i++){
                mat[i][(int)(tam/2)-1]=false;
            }
        }
        if(p.getXpos()+1>=length){
            for(int i=0;i<tam;i++){
                mat[1+(int)(tam/2)][i]=false;
            }
        }
        if(p.getXpos()-1<0){
            for(int i=0;i<tam;i++){
                mat[(int)(tam/2)-1][i]=false;
            }
        }
        
        //*/
        int x=tam/2;
        
        if(mat[x-1][x]!=0){
            for(int i=0;i<tam;i++){
                if(mat[x-1][i]==0)
                    mat[x-1][i]=-1;
            }
        }
        if(mat[x+1][x]!=0){
            for(int i=0;i<tam;i++){
                if(mat[x+1][i]==0)
                    mat[x+1][i]=-1;
            }
        }
        if(mat[x][x-1]!=0){
            for(int i=0;i<tam;i++){
                if(mat[i][x-1]==0)
                    mat[i][x-1]=-1;
            }
        }
        if(mat[x][x+1]!=0){
            for(int i=0;i<tam;i++){
                if(mat[i][x+1]==0)
                    mat[i][x+1]=-1;
            }
        }
        return mat;
    }
    
    
    private void ackSensor(ActionDataAgent data){
        //try {
            //System.out.println("-------------------ACK_SENSOR :D--------- "+data.getAlias());
            AgentExplorerState state = (AgentExplorerState) this.getAgent().getState();
            state.resetContMsOld();
            List<SeenObject> enemies=new ArrayList<>();
            int tam=(int)state.getSightRange()*2+1;
            int [][]mat=new int[tam][tam];
            for (int i=0;i<tam;i++){
                for (int j=0;j<tam;j++){
                    mat[i][j]=-1;
                }
            }
            state.setPosition(data.getPosition());
            int k=1;
            for (SeenObject so:data.getSeenObjects()){
                switch(so.getType()){
                    case(-1):state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); break;//Walls
                    case(0):break;//Protector
                    case(1):break;//Protector
                    case(2):break;//Exprorator
                    case(3):break;//Hostage
                    case(4):enemies.add(so);break;//Enemmy
                }
                if (so.getType()>0){
                    mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=k;
                }else{
                    mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=so.getType();
                }
                if (so.getType()!=-1){
                    state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(),0);
                }
                k++;
            }
            mat=border(mat, data.getPosition(), state.getEdifice().getWidth(),state.getEdifice().getLength(),tam);
            mat[tam/2][tam/2]=-1;
            List<Motion> movements=new ArrayList<>();
            int offset=(int)(tam/2)-1;
            for (int i=offset;i<offset+3;i++){
                for (int j=offset;j<offset+3;j++){
                    if (mat[i][j]==0){
                       movements.add(new Motion(state.getXpos()+i-1-offset, state.getYpos()+j-1-offset, state.getIdfloor()));
                       state.setModelEdiffice(state.getIdfloor(), movements.get(movements.size()-1).getXpos(), movements.get(movements.size()-1).getYpos(),0);
                    }
                }
                
            }
            
            //System.out.println(state.getPosition());
            //System.out.println(state.getEdifice());
            
            /*
            if(!state.getHostages().isEmpty()){
                callHostages();
            }
            
            if(!enemies.isEmpty()){
                shootEnemies();
            }
            */
            state.marckConsecutive(data.getIn_reply_to());
            int reply_with=state.getNextConsecutive();
            int in_reply_to=data.getReply_with();
            
            if (state.nextMotion(movements)){
                moveAgent(reply_with, in_reply_to, state.getMotion());
            }else{
                if(state.isDeadLock()){
                    System.out.println("------------------------- DEADLOCK --------------------------- "+state.getAlias()+" "+state.getDesiredGoal());
                    SeenObject agent=state.solutionDeadLock(data.getSeenObjects(), mat);
                    if (agent!=null){
                        sendMessageNegotiation(reply_with, in_reply_to,agent);
                    }
                    disableController(reply_with, in_reply_to);
                }else if (state.isFullExplorationMap()){
                            disableController(reply_with, in_reply_to);
                        }else{
                            msnSensor(reply_with, in_reply_to);
                            }
                
            }
       /*
        } catch (Exception e) {
            System.out.println(" xxxxxxxxxxxxxxxxxxxxxxxxxxx xxxx ERROR ACK SENSOR "+e);
        }
            */
    }

    private void callHostages() {

    }

    private void shootEnemies() {
    
    }

    private void msnSensor(int reply_with,int in_reply_to) {
        boolean sw=false;
        //do{
            try {
                AgentState state = (AgentState) this.getAgent().getState();
                ActionDataAgent actionData = new ActionDataAgent(reply_with,in_reply_to,state.getType(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
                Agent.sendMessage(SensorsAgentGuardJME.class,Const.World, actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    }

    private void disableController(int reply_with,int in_reply_to) {
        boolean sw=false;
        //do{
            try {
                AgentState state = (AgentState) this.getAgent().getState();
                ActionData actionData = new ActionData(reply_with,in_reply_to,state.getType(), state.getAlias(), "disableController");
                Agent.sendMessage(UpdateGuardJME.class,Const.World, actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    }
    
    private void nack(ActionDataAgent data, AgentState state) {
        //ReportBESA.info("-------------------NAK :(--------- ");
        state.setPosition(data.getPosition());
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             System.out.println("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------NACK:D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }

         }
         msnSensor(data.getIn_reply_to(),data.getReply_with());
    }
    
    private void ackNegotiation(ActionDataAgent data, AgentState state) throws InterruptedException {
        //ReportBESA.info("-------------------ACK NEGOTIATION  :D--------- ");
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             System.out.println("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------NACK:D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }

         }
         if (state.isWinNegotiation()){
             Thread.sleep(500);
         }
         state.setDeadLock(false);
         ReportBESA.info("-------------------ACK NEGOTIATION  :D--------- "+state.getAlias());
         msnSensor(data.getIn_reply_to(),data.getReply_with());
    }

    private void ack(ActionDataAgent data, AgentState state) {
        //ReportBESA.info("-------------------ACK:D--------- "+((AgentState) this.getAgent().getState()).getAlias());
        state.setPosition(data.getPosition());
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             ReportBESA.info("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------ACK:D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }

         }
         msnSensor(data.getIn_reply_to(),data.getReply_with());
    }

    private void sendMessageNegotiation(int reply_with, int in_reply_to, SeenObject agent) {
        boolean sw=false;
        //do{
            try {
                AgentState state = (AgentState) this.getAgent().getState();
                NegotiationData actionData = new NegotiationData(reply_with,in_reply_to,state.getType(), state.getAlias(),state.getGoal(),Utils.randomInteger(0, 10));
                Agent.sendMessage(AgentNegotiationGuard.class,agent.getName(), actionData);
                sw=true;
                state.setNegotiationData(actionData);
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    
    
    }

    
    
    
    
}
