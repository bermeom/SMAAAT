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
import BESAFile.Data.ChangeFloorData;
import BESAFile.Data.FolowingData;
import BESAFile.Data.NegotiationData;
import BESAFile.Data.ShootAgentDataJME;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import BESAFile.Model.SeenWall;
import BESAFile.World.Behavior.ChangeFloorGuardJME;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import BESAFile.World.Behavior.ShootAgentJME;
import BESAFile.World.Behavior.UpdateGuardJME;
import BESAFile.World.Model.ModelEdifice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            if(!state.getAlias().equals(data.getAlias())||state.getLife()<=0){
                return;
            }
            switch (data.getAction()) {
                case "move":
                    //ReportBESA.info("-------------------Move:D--------- "+((AgentState) this.getAgent().getState()).getAlias());
                    moveAgent(data.getReply_with(),data.getIn_reply_to(),data.getMotion());
                    break;
                case "NACK":
                    nack(data,state);
                    break;
                case "NACK_DC"://disable controles
                    nack_dc(data,state);
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
                case("NACK_CHANGE_FLOOR"):
                    nack_change_floor(data,state);
                    break;
                case("ACK_CHANGE_FLOOR"):
                    ackChangeFloor(data,state);
                    break;
                case("ACK_SHOOT"):
                    ackShoot(data,state);
                    break;

            }
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: MAIN xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " +e);
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
 
        }
    }

    public void moveAgent(int reply_with,int in_reply_to,Motion motion) {
        try {
            AgentState state = (AgentState) this.getAgent().getState();
            ActionData ad = new ActionData(reply_with,in_reply_to,state.getType(), state.getAlias(),(float)state.getSpeed(),motion,state.getPosition(), "move");
            Agent.sendMessage(UpdateGuardJME.class,Const.World+state.getIdfloor(), ad);
        } catch (Exception e) {
               System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR:  moveAgent xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +e);

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
    
    
    
    private void ackSensor(ActionDataAgent data) throws InterruptedException, IOException{
        //try {
            //System.out.println("-------------------ACK_SENSOR :D--------- "+data.getAlias());
            AgentEnemyState state = (AgentEnemyState) this.getAgent().getState();
            state.resetContMsOld();
            List<SeenObject> agentsPandE=new ArrayList<SeenObject>();
            List<SeenObject> hostage=new ArrayList<SeenObject>();
            List<Motion> climbStairs=new ArrayList<Motion>();
            List<Motion> downStairs=new ArrayList<Motion>();
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
                    case(-4):downStairs.add(new Motion(so.getPosition())); 
                            state.addDownStairs(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos());
                            state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); 
                            break;//Walls
                    case(-3):climbStairs.add(new Motion(so.getPosition())); 
                            state.addClimbtairs(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos());    
                            state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); 
                            break;//Walls
                    case(-2):state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); break;//Walls
                    case(-1):state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); break;//Walls
                    case(0):break;//Protector
                    case(1):agentsPandE.add(so);break;//Protector
                    case(2):agentsPandE.add(so);break;//Exprorator
                    case(3):hostage.add(so);break;//Hostage
                    case(4):break;//Enemmy
                }
                if (so.getType()>0){
                    mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=k;
                }else{
                    mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=so.getType();
                }
                if (so.getType()!=-1&&so.getType()!=-4&&so.getType()!=-3){
                    state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(),0);
                }
                k++;
            }
            if(state.getPosition().getIdfloor()==0&&climbStairs.size()>0&&!state.getDesiredGoals().isEmpty()&&state.getGoalType()!=-3){
                Motion m=state.getMovementsRandom(climbStairs);
                System.out.println(">>>>>>>>>>>>>>>> "+state.getGoalType()+" +++++++++++++++++++ "+state.getEdifice().getPostGridFloor(new Position(m)));
                while (!state.getDesiredGoals().isEmpty()){
                    state.getDesiredGoals().pop();
                }
                state.addGoal(new Position(m), true, true, state.getEdifice().getPostGridFloor(new Position(m)));
                //System.out.println("ENTRO");
            }
            
            mat=Utils.border(mat, data.getPosition(), state.getEdifice().getWidth(),state.getEdifice().getLength(),tam);
            mat[tam/2][tam/2]=-1;
            List<Motion> movements=new ArrayList<>();
            int offset=(int)(tam/2)-1;
            for (int i=offset;i<offset+3;i++){
                for (int j=offset;j<offset+3;j++){
                    if (mat[i][j]==0||mat[i][j]==-4||mat[i][j]==-3){
                       movements.add(new Motion(state.getXpos()+i-1-offset, state.getYpos()+j-1-offset, state.getIdfloor()));
                       //state.setModelEdiffice(state.getIdfloor(), movements.get(movements.size()-1).getXpos(), movements.get(movements.size()-1).getYpos(),0);
                    }
                }
                
            }
            
            //System.out.println(state.getPosition());
            //System.out.println(state.getEdifice());
            
            state.marckConsecutive(data.getIn_reply_to());
            int reply_with=state.getNextConsecutive();
            int in_reply_to=data.getReply_with();
            
           
            if(state.getHostages().size()<=2&&hostage.size()>0){
                callHostages(hostage,state);
            }
           
            if(!agentsPandE.isEmpty()&&Utils.randomIntegerMA(0, 100)%2==0){
                disableController(reply_with, in_reply_to);
                shootProtectorOrExprorator(reply_with, in_reply_to, agentsPandE);
                return;
            }
            
            //*/
            state.setMotion(state.getMovementsRandom(movements));
            if (!state.getMotion().isIsNull()){
                if (state.getPosition().isEquals(state.getMotion())){
                    disableController(reply_with, in_reply_to);
                    Thread.sleep(Utils.randomIntegerMA(0,100));
                    msnSensor(reply_with, in_reply_to);
                }else{
                    //disableController(reply_with, in_reply_to);
                    //printMatANDMotion(mat,state.getMotion());
                    //stop();
                    moveAgent(reply_with, in_reply_to, state.getMotion());
                }
            }else{
                if (state.isChangeFloor()){
                    disableController(reply_with, in_reply_to);
                    sendChangeFloor(reply_with, in_reply_to);
                }else if(state.isDeadLock()){
                        System.out.println("------------------------- DEADLOCK --------------------------- "+state.getAlias()+" "+state.getDesiredGoal());
                        SeenObject agent=state.solutionDeadLock(data.getSeenObjects(), mat);
                        if (agent!=null){
                            sendMessageNegotiation(reply_with, in_reply_to,agent);
                        }
                        disableController(reply_with, in_reply_to);
                }else if (state.isFullExplorationMap(state.getPosition().getIdfloor())){
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

    private void callHostages(List<SeenObject> hostage, AgentEnemyState state) {
                boolean sw=false;
                for (SeenObject hSO:hostage){
                    sw=false;
                    for (String h:state.getHostages()){
                        if(h.equals(hSO.getName())){
                            sw=true;
                        }
                    }
                    if(!sw){
                        state.getHostages().add(hSO.getName());
                        System.out.println("SEND MSN");
                        FolowingData fd=new FolowingData(1,1, state.getAlias(), state.getAlias(),"REPLAY");
                        Agent.sendMessage(FollowHostageGuard.class, hSO.getName(), fd);
                    }
                }
    }

   private void shootProtectorOrExprorator(int reply_with,int in_reply_to,List<SeenObject> agents) {
        AgentState state = (AgentState) this.getAgent().getState();
        Position target=Utils.selectRandomSeenObjects(agents).getPosition();
        ShootAgentDataJME data=new ShootAgentDataJME(reply_with, in_reply_to,state.getAlias(), state.getType(), state.getPosition(), target);
        Agent.sendMessage(ShootAgentJME.class, Const.World+state.getIdfloor(), data);
    }

  
    private void msnSensor(int reply_with,int in_reply_to) {
        boolean sw=false;
        //do{
            try {
                AgentState state = (AgentState) this.getAgent().getState();
                ActionDataAgent actionData = new ActionDataAgent(reply_with,in_reply_to,state.getType(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
                Agent.sendMessage(SensorsAgentGuardJME.class,Const.World+state.getIdfloor(), actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR:  msnSensor xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +e);
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
                ActionData actionData = new ActionData(reply_with,in_reply_to,state.getType(),state.getPosition(), state.getAlias(), "disableController");
                Agent.sendMessage(UpdateGuardJME.class,Const.World+state.getIdfloor(), actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: disableController  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +e);
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    }
    
    
    private void sendChangeFloor(int reply_with,int in_reply_to) {
        boolean sw=false;
        //do{
            try {
                AgentState state = (AgentState) this.getAgent().getState();
                ChangeFloorData actionData = new ChangeFloorData(reply_with, in_reply_to, state.getGoalType(),state.getType(), state.getPosition(),state.getAlias());
                Agent.sendMessage(ChangeFloorGuardJME.class,Const.World+state.getIdfloor(), actionData);
                sw=true;
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: sendChangeFloor xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +e);
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
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             ReportBESA.info("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------ACK:D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }
             state.setPosition(data.getPosition());
         }else{
             state.setPosition(data.getPosition());
         }
         msnSensor(data.getIn_reply_to(),data.getReply_with());
    }
    
    private void ackShoot(ActionDataAgent data, AgentState state) {
        //ReportBESA.info("-------------------ACK SHOOT:D--------- "+((AgentState) this.getAgent().getState()).getAlias());
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
    
    
    private void ackChangeFloor(ActionDataAgent data, AgentState state) {
         //ReportBESA.info("-------------------ACK CHANGE FLOOR:D--------- "+((AgentState) this.getAgent().getState()).getAlias());
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             ReportBESA.info("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------ACK:D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }
             state.setPosition(data.getPosition());
         }else{
             state.setPosition(data.getPosition());
         }
         state.getDesiredGoals().pop();
         state.setChangeFloor(false);
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
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERROR: sendMessageNegotiation xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +e);
                ReportBESA.error(e);
                sw=false;
            }
        //}while(!sw);
    
    
    }
    
    private void stop() throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String linea=br.readLine();
    }

    private void nack_dc(ActionDataAgent data, AgentState state) {
        //ReportBESA.info("-------------------NAK_DC :(--------- ");
        state.setPosition(data.getPosition());
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             System.out.println("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------NAK_DC :D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }

         }
         
    }

    private void printMatANDMotion(int[][] mat, Motion motion) {
        System.out.println(motion);
        mat[mat.length/2][mat.length/2]=-2;
        for (int i=0;i<mat.length;i++){
                for (int j=0;j<mat[i].length;j++){
                    System.out.print("\t"+mat[i][j]);
                }
                System.out.println("");
                
            }
    }

    private void nack_change_floor(ActionDataAgent data, AgentState state) {
        ReportBESA.info("-------------------NACK_CHANGE_FLOOR :(--------- ");
        state.setPosition(data.getPosition());
         if (!state.getIdConsecutive(data.getIn_reply_to())){
             state.plusPlusContMsOld();
             System.out.println("+++++++++++++++++++++++++++++++++++  Message OLD "+state.getAlias()+" "+state.getContMessagesOld());
             if (!state.isExceededContMsOld()){
                 ReportBESA.info("-------------------NAK_DC :D OLD--------- "+((AgentState) this.getAgent().getState()).getAlias());
                 return ;
             }

         }
    }

    
    
    
    
}

