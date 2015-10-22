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
import BESAFile.Agent.State.AgentHostageState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.AgentStateTest;
import BESAFile.Agent.State.Motion;
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
import simulation.utils.Const;
import simulation.utils.Utils;

/**
 *
 * @author berme_000
 */
public class AgentHostageMoveGuard  extends GuardBESA  {
    
    private final int type=4;
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
                case "NAK":
                    //System.out.println("-------------------NAK :(--------- ");
                    //moveAgent(data);
                    break;
                case "ACK":
                     //System.out.println("-------------------ACK:D--------- ");
                     Thread.sleep(Const.sleep);
                     msnSensor();
                    //moveACKAgent(data);
                    break;
                case("ACK_SENSOR"):
                    //System.out.println("-------------------ACK_SENSOR :D--------- ");
                    ackSensor(data);
                    break;

            }
        } catch (Exception e) {
        }
    }

    public void moveAgent(ActionDataAgent data) {
        AgentState state = (AgentState) this.getAgent().getState();
        System.out.println(data.getViewDirection());
        ActionData ad = new ActionData(state.getType(), state.getAlias(),(float)state.getSpeed(),data.getMotion(),data.getPosition(), "move");
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
        ActionDataAgent actionData = new ActionDataAgent(state.getType(),state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
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
    
    private boolean[][] border(boolean [][]mat,Vector3D position,Vector3D pos){
        double borderXPlus=Const.x+Const.width;
        double borderXMinus=Const.x-Const.width;
        double borderZPlus=Const.z+Const.length;
        double borderZMinus=Const.z-Const.length;
        double delta=1.8f;
        if(compareDistance(borderXPlus,position.getX(), delta-0.5)){
            for(int i=0;i<3;i++){
                mat[i][2]=false;
            }
        }
        if(compareDistance(borderXMinus,position.getX(),  delta)){
            for(int i=0;i<3;i++){
                mat[i][0]=false;
            }
        }
        if (compareDistance(borderZPlus,position.getZ(),  delta)){
            for(int i=0;i<3;i++){
                mat[2][i]=false;
            }
        }
        if(compareDistance(borderZMinus,position.getZ(),  delta)){
            for(int i=0;i<3;i++){
                mat[0][i]=false;
            }
        }
        
        if(!mat[0][1]){
            for(int i=0;i<3;i++){
                mat[0][i]=false;
            }
        }
        if(!mat[2][1]){
            for(int i=0;i<3;i++){
                mat[2][i]=false;
            }
        }
        if(!mat[1][0]){
            for(int i=0;i<3;i++){
                mat[i][0]=false;
            }
        }
        if(!mat[1][2]){
            for(int i=0;i<3;i++){
                mat[i][2]=false;
            }
        }
        return mat;
    }
    
    
    
    private void ackSensor(ActionDataAgent data){
            AgentHostageState state = (AgentHostageState) this.getAgent().getState();
            /*
            List<SeenObject> enemies=new ArrayList<>();
            List<SeenObject> protectors=new ArrayList<>();
            boolean [][]mat=new boolean[3][3];
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    mat[i][j]=true;
                }
            }
            Vector3D pos=new Vector3D(1, 0, 1);
            for(SeenObject so:data.getSeenObjects()){
                switch(Const.getType(so.getName())){
                    case(1):protectors.add(so);break;//Protector
                    case(2):break;//Exprorator
                    case(3): break;//Hostage
                    case(4):enemies.add(so);break;//Enemmy
                }
                Vector3D directionObst=(vectorDirection(data.getPosition(), so.getPosition()));
                mat[(int)(1+directionObst.getZ())][(int)(1+directionObst.getX())]=false;
            }
            for(SeenWall sw:data.getSeenWalls()){
                state.setModelEdiffice(sw.getIdfloor(), sw.getXpos(), sw.getYpos(), sw.getWall());
                Vector3D directionObst=(vectorDirection(data.getPosition(), sw.getPosition()));
                mat[(int)(1+directionObst.getZ())][(int)(1+directionObst.getX())]=false;
            }
            
            mat=border(mat, data.getPosition(), pos);
            mat[1][1]=false;
            List<Vector3D> motions=new ArrayList<>();
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (mat[i][j]){
                        motions.add(Utils.direction(i, j)); 
                    }
                }
            }
            if(!protectors.isEmpty()){
                callProtector(protectors);
            }
            
            if(!enemies.isEmpty()){
                shootEnemies();
            }
            Vector3D direction;
            if (motions.size()>0){
                Random random=new Random(System.currentTimeMillis());
                direction=motions.get(random.nextInt(motions.size()));
                if(motions.size()==8){
                    direction=state.getDirection();
                }
            }else{
                direction=new Vector3D();
            }            
            state.setDirection(direction);
            ActionDataAgent sod = new ActionDataAgent(data.getType(),data.getAlias(),"move",direction);
            Agent.sendMessage(Const.getGuardMove(state.getType()), data.getAlias(), sod );
            //*/
    }

    private void callHostages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
    }

    private void shootEnemies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void msnSensor() {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionDataAgent actionData = new ActionDataAgent(state.getType(),state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(),state.getPosition(), "Sensing");
        EventBESA event = new EventBESA(SensorsAgentGuardJME.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(Const.World);
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }
    }

    private void callProtector(List<SeenObject> protectors) {
        AgentState state = (AgentState) this.getAgent().getState();
        for (SeenObject p:protectors ){
            ActionDataAgent actionData = new ActionDataAgent(state.getType(), state.getAlias());
            EventBESA event = new EventBESA(HELPAgentProtectorGuard.class.getName(), actionData);
            AgHandlerBESA ah;
            try {
                ah = getAgent().getAdmLocal().getHandlerByAlias(p.getName());
                ah.sendEvent(event);
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
            }
        }

    
    }
}
