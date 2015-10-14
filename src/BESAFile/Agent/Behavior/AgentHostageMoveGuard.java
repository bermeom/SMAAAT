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
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.AgentStateTest;
import BESAFile.Agent.State.MotionTest;
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
                    ReportBESA.info("-------------------Move:D--------- ");
                    moveAgent(data,3);

                    break;
                case "NAK":
                    System.out.println("-------------------NAK :(--------- ");
                    //moveAgent(data);
                    break;
                case "ACK":
                     System.out.println("-------------------ACK:D--------- ");
                     Thread.sleep(900);
                     msnSensor();
                    //moveACKAgent(data);
                    break;
                case("ACK_SENSOR"):
                    System.out.println("-------------------ACK_SENSOR :D--------- ");
                    ackSensor(data);
                    break;

            }
        } catch (Exception e) {
        }
    }

    public void moveAgent(ActionDataAgent data,float speed) {
        AgentState state = (AgentState) this.getAgent().getState();
        System.out.println(data.getViewDirection());
        ActionData ad = new ActionData(state.getType(),0, 1, state.getIdfloor(), state.getAlias(), "move",data.getViewDirection(),speed);
        Agent.sendMessage(UpdateGuardJME.class,Const.World, ad);

    }

    public boolean intervalValidation(int n, int limit) {
        return n >= 0 && n < limit;
    }

    public Queue<MotionTest> generationPossibleMotions(AgentStateTest aState) {
        Queue<MotionTest> possibleMotions = new ArrayDeque<MotionTest>();
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
                possibleMotions.add(new MotionTest(newX, newY, idFloor));
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
        aState.setPossibleMotions(new ArrayDeque<MotionTest>());
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
        ActionDataAgent actionData = new ActionDataAgent(state.getType(),state.getIdfloor(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(), "Sensing");
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
    
    private boolean positionEqual(Vector3D pActual,Vector3D p,double delta){
            return  compareDistance(pActual.getX(),p.getX(), delta)&&compareDistance(pActual.getY(),p.getY(), delta)&&compareDistance(pActual.getZ(),p.getZ(), delta);
    }
   
    private Vector3D vectorDirection(Vector3D f,Vector3D a){
            System.out.println("f "+f);
            System.out.println("a "+a);
            return (new Vector3D((a.getX()-f.getX()), a.getY()-f.getY(), a.getZ()-f.getZ())).normalize1();
        
    }
    
    private boolean[][] border(boolean [][]mat,Vector3D position,Vector3D pos){
        double borderXPlus=Const.x+Const.width;
        double borderXMinus=Const.x-Const.width;
        double borderZPlus=Const.z+Const.length;
        double borderZMinus=Const.z-Const.length;
        double delta=1.8f;
        System.out.println(position.getX()+" "+position.getZ()+" "+borderZMinus);
        if(compareDistance(borderXPlus,position.getX(), delta-0.5)){
            System.out.println("x+");
            for(int i=0;i<3;i++){
                mat[i][2]=false;
            }
        }
        if(compareDistance(borderXMinus,position.getX(),  delta)){
            System.out.println("x-");
            for(int i=0;i<3;i++){
                mat[i][0]=false;
            }
        }
        if (compareDistance(borderZPlus,position.getZ(),  delta)){
            System.out.println("z+");
            for(int i=0;i<3;i++){
                mat[2][i]=false;
            }
        }
        if(compareDistance(borderZMinus,position.getZ(),  delta)){
            System.out.println("z-");
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
            List<SeenObject> enemies=new ArrayList<>();
            boolean [][]mat=new boolean[3][3];
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    mat[i][j]=true;
                }
            }
            Vector3D pos=new Vector3D(1, 0, 1);
            for(SeenObject so:data.getSeenObjects()){
                switch(Const.getType(so.getName())){
                    case(1):break;//Protector
                    case(2):break;//Exprorator
                    case(3): break;//Hostage
                    case(4):enemies.add(so);break;//Enemmy
                }
            }
            for(SeenWall sw:data.getSeenWalls()){
                state.setModelEdiffice(sw.getIdfloor(), sw.getXpos(), sw.getYpos(), sw.getWall());
                Vector3D directionObst=(vectorDirection(data.getPosition(), sw.getPosition()));
                mat[(int)(1+directionObst.getZ())][(int)(1+directionObst.getX())]=false;
                System.out.println(directionObst);
            }
            
            mat=border(mat, data.getPosition(), pos);
            mat[1][1]=false;
            List<Vector3D> motions=new ArrayList<>();
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                  
                    if(mat[i][j]){
                        int x,z;
                        if(i==1){
                            z=0;
                            if(j==0){
                                x=-1;
                            }else{
                                x=1;
                            }
                        }else if(j==1){
                                x=0;
                                if(j==0){
                                    z=-1;
                                }else{
                                    z=1;
                                }
                            }else{
                                if(i==0&&j==2){
                                    z=-1;
                                    x=1;
                                }else if (i==2&&j==0){
                                            z=1;
                                            x=-1;
                                        }else{
                                            x=i-1;
                                            z=j-1;
                                            }
                                  
                                }
                        motions.add(new Vector3D(x, 0, z));
                        System.out.print("0 ");
                    }else
                        System.out.print("1 ");
                    
                }
                System.out.println("");
            }
            
            
            if(!enemies.isEmpty()){
                shootEnemies();
            }
            Random random=new Random(System.currentTimeMillis());
            Vector3D direction=motions.get(random.nextInt(motions.size()));
            if(motions.size()==8){
                direction=state.getDirection();
            }            
            //System.out.println(direction);
            state.setDirection(direction);
            ActionDataAgent sod = new ActionDataAgent(data.getType(),data.getAlias(),"move",direction);
            //System.out.println(Const.getGuardMove(state.getType()).getName());
            Agent.sendMessage(Const.getGuardMove(state.getType()), data.getAlias(), sod );
    }

    private void callHostages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void shootEnemies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void msnSensor() {
        AgentState state = (AgentState) this.getAgent().getState();
        ActionDataAgent actionData = new ActionDataAgent(state.getType(),state.getIdfloor(), state.getSightRange(), state.getRadius(), state.getHeight(), state.getAlias(), "Sensing");
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
