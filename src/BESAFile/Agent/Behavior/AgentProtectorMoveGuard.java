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
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.AgentStateTest;
import BESAFile.Agent.State.MotionN;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import BESAFile.Model.SeenWall;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import BESAFile.World.Behavior.UpdateGuard;
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
public class AgentProtectorMoveGuard extends GuardBESA {

    private final int type=1;
    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            switch (data.getAction()) {
                case "move":
                    //ReportBESA.info("-------------------Move:D--------- ");
                    moveAgent(data);

                    break;
                case "NACK1":
                    System.out.println("-------------------NAK :(--------- ");
                    msnSensor();
                    break;
                case "ACK":
                     System.out.println("-------------------ACK:D--------- ");
                     AgentState state = (AgentState) this.getAgent().getState();
                     state.setPosition(data.getPosition());
                     msnSensor();
                    //moveACKAgent(data);
                    break;
                case("ACK_SENSOR"):
                    System.out.println("-------------------ACK_SENSOR :D--------- ");
                    ackSensor(data);
                    break;

            }
        } catch (Exception e) {
            System.out.println("ERROR:");
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
 
        }
    }

    public void moveAgent(ActionDataAgent data) {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionData ad = new ActionData(state.getType(), state.getAlias(),(float)state.getSpeed(),data.getMotion(),state.getPosition(), "move");
        Agent.sendMessage(UpdateGuardJME.class,Const.World, ad);

    }

    public boolean intervalValidation(int n, int limit) {
        return n >= 0 && n < limit;
    }

    public Queue<Motion> generationPossibleMotions(AgentStateTest aState) {
        Queue<Motion> possibleMotions = new ArrayDeque<Motion>();
        ModelEdifice edifice = aState.getEdifice();
        int x, y, idFloor;
        x = aState.getXpos();
        y = aState.getYpos();
        idFloor = aState.getIdfloor();
        int movX[] = {1, 0, 0, -1};
        int movY[] = {0, 1, -1, 0};
        int newX, newY;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        //randomized files
        Collections.shuffle(list);
        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
        Collections.shuffle(list, new Random(seed));
        for (int i : list) {
            newX = movX[i] + x;
            newY = movY[i] + y;
            if (intervalValidation(newX, edifice.getWidth()) && intervalValidation(newY, edifice.getLength()) && edifice.getFloor(idFloor).get(newX, newY) == '0') {
                possibleMotions.add(new Motion(newX, newY, idFloor));
            }
        }

        return possibleMotions;
    }

    private void moveACKAgent(ActionData data) throws InterruptedException {
        AgentStateTest aState = (AgentStateTest) this.getAgent().getState();
        //System.out.println(" >>>>>>> "+aState.getXpos()+" - "+aState.getYpos());
        aState.setXpos(data.getXpos());
        aState.setYpos(data.getYpos());
        aState.setIdfloor(data.getIdfloor());
        aState.setPossibleMotions(new ArrayDeque<Motion>());
        data.setAlias(aState.getAlias());
        data.setAction("move");
        Thread.sleep(1000);
        EventBESA event = new EventBESA(AgentMoveGuard.class.getName(), data);
        AgHandlerBESA ah;
        boolean sw = true;
        do {
            try {
                ah = getAgent().getAdmLocal().getHandlerByAlias(data.getAlias());
                ah.sendEvent(event);
                sw = false;
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
                sw = true;
            }
        } while (sw);
    }

    public void dataSensorRequest() {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionDataAgent actionData = new ActionDataAgent(state.getType(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
        EventBESA event = new EventBESA(SensorsAgentGuardJME.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(Const.World);
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
        //*/
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
            AgentProtectorState state = (AgentProtectorState) this.getAgent().getState();
            List<SeenObject> enemies=new ArrayList<>();
            int tam=(int)state.getSightRange()*2+1;
            boolean [][]mat=new boolean[tam][tam];
            for (int i=0;i<tam;i++){
                for (int j=0;j<tam;j++){
                    mat[i][j]=true;
                }
            }
            state.setPosition(data.getPosition());
            System.out.println(data.getSeenObjects().size());
            for (SeenObject so:data.getSeenObjects()){
                System.out.println(so.getPosition()+" ------- "+so.getType()+"-> "+(data.getPosition().getXpos()-so.getPosition().getXpos())+" "+(data.getPosition().getYpos()-so.getPosition().getYpos()));
                switch(so.getType()){
                    case(-1):state.setModelEdiffice(so.getPosition().getIdfloor(), so.getPosition().getXpos(), so.getPosition().getYpos(), so.getType()); break;//Walls
                    case(1):break;//Protector
                    case(2):break;//Exprorator
                    case(3):state.addHostage(so.getName()); break;//Hostage
                    case(4):enemies.add(so);break;//Enemmy
                }
                System.out.println(so.getName()+" --------- "+(state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos())+" "+(state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()));
                mat[state.getSightRange()+so.getPosition().getXpos()-data.getPosition().getXpos()][state.getSightRange()+so.getPosition().getYpos()-data.getPosition().getYpos()]=false;
            }
            mat=border(mat, data.getPosition(), state.getEdifice().getWidth(),state.getEdifice().getLength(),tam);
            mat[tam/2][tam/2]=false;
            List<Motion> motions=new ArrayList<>();
            int offset=(int)(tam/2)-1;
            System.out.println("**********************************" +tam+(int)(tam/2)+" oFFSET "+offset);
            for (int i=offset;i<offset+3;i++){
                for (int j=offset;j<offset+3;j++){
                    System.out.print(mat[i][j]+" ");
                    if (mat[i][j]){
                       motions.add(new Motion(state.getXpos()+i-1-offset, state.getYpos()+j-1-offset, state.getIdfloor()));
                    }
                }
                System.out.println("");
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
            System.out.println("========== "+motions.size());
            Motion motion;
            if (motions.size()>0){
                Random random=new Random(System.currentTimeMillis());
                motion=motions.get(random.nextInt(motions.size()));
            }else{
                motion=new Motion(state.getXpos(),state.getYpos(), state.getIdfloor());
            }
            System.out.println("Motion: "+motion+" Position: "+state.getPosition());
            state.setDirection(state.getDirection());//int xpos, int ypos, int idfloor,float speed
            ActionDataAgent sod = new ActionDataAgent(data.getType(),data.getAlias(),motion,data.getPosition(),(float)state.getSpeed(),"move");
            Agent.sendMessage(Const.getGuardMove(state.getType()), data.getAlias(), sod );
    }

    private void callHostages() {

    }

    private void shootEnemies() {
    
    }

    private void msnSensor() {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionDataAgent actionData = new ActionDataAgent(state.getType(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
        EventBESA event = new EventBESA(SensorsAgentGuardJME.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(Const.World);
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
    }
    
}
