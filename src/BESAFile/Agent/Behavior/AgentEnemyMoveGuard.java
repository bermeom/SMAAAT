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
import BESAFile.Agent.State.AgentEnemyState;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.AgentStateTest;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
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
public class AgentEnemyMoveGuard  extends GuardBESA  {
    
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
    
    private boolean[][] border(boolean [][]mat, Position p, int width,int length ,int tam){
        if(p.getYpos()+1>=width){
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
        int x=tam/2;
        
        if(!mat[x-1][x]){
            for(int i=0;i<tam;i++){
                mat[x-1][i]=false;
            }
        }
        if(!mat[x+1][x]){
            for(int i=0;i<tam;i++){
                mat[x+1][i]=false;
            }
        }
        if(!mat[x][x-1]){
            for(int i=0;i<tam;i++){
                mat[i][x-1]=false;
            }
        }
        if(!mat[x][x+1]){
            for(int i=0;i<tam;i++){
                mat[i][x+1]=false;
            }
        }
        return mat;
    }
    
    
    private void ackSensor(ActionDataAgent data){
        try {
            //System.out.println("-------------------ACK_SENSOR :D--------- "+data.getAlias());
            AgentEnemyState state = (AgentEnemyState) this.getAgent().getState();
            state.resetContMsOld();
            List<SeenObject> enemies=new ArrayList<>();
            int tam=(int)state.getSightRange()*2+1;
            boolean [][]mat=new boolean[tam][tam];
            for (int i=0;i<tam;i++){
                for (int j=0;j<tam;j++){
                    mat[i][j]=true;
                }
            }
            state.setPosition(data.getPosition());
            for (SeenObject so:data.getSeenObjects()){
                switch(so.getType()){
                    case(-1):state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); break;//Walls
                    case(1):break;//Protector
                    case(2):break;//Exprorator
                    case(3):state.addHostage(so.getName()); break;//Hostage
                    case(4):enemies.add(so);break;//Enemmy
                }
                mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=false;
            }
            mat=border(mat, data.getPosition(), state.getEdifice().getWidth(),state.getEdifice().getLength(),tam);
            mat[tam/2][tam/2]=false;
            List<Motion> motions=new ArrayList<>();
            int offset=(int)(tam/2)-1;
            for (int i=offset;i<offset+3;i++){
                for (int j=offset;j<offset+3;j++){
                    if (mat[i][j]){
                       motions.add(new Motion(state.getXpos()+i-1-offset, state.getYpos()+j-1-offset, state.getIdfloor()));
                    }
                }
                
            }
            /*
            //System.out.println(state.getEdifice());
            if(!state.getHostages().isEmpty()){
                callHostages();
            }
            
            if(!enemies.isEmpty()){
                shootEnemies();
            }
            */
            Motion motion;
            state.marckConsecutive(data.getIn_reply_to());
            int reply_with=state.getNextConsecutive();
            int in_reply_to=data.getReply_with();
            if (motions.size()>0){
                Random random=new Random(System.currentTimeMillis());
                int n=0;
                do{
                    n= random.nextInt(motions.size());
                }while(n<0 || n>=motions.size());
                motion=motions.get(n);
               
                moveAgent(reply_with, in_reply_to, motion);
            }else{
                msnSensor(reply_with, in_reply_to);
            }
            
        } catch (Exception e) {
            System.out.println(" xxxxxxxxxxxxxxxxxxxxxxxxxxx xxxx ERROR ACK SENSOR");
        }
            
    }

    private void callHostages() {

    }

    private void shootEnemies() {
    
    }

    private void msnSensor(int reply_with,int in_reply_to) {
        boolean sw=false;
        do{
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
        }while(!sw);
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
    
}
