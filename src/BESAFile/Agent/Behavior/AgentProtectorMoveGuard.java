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
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.Motion;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.Behavior.SensorsAgentGuardJME;
import BESAFile.World.Behavior.UpdateGuard;
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
public class AgentProtectorMoveGuard extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            
            switch (data.getAction()) {
                case "move":
                            ReportBESA.info("-------------------Move:D--------- ");
                            /*
                            AgentProtectorState aState= (AgentProtectorState)this.getAgent().getState();
                            aState.getGa().getwNavControl().move(data.getTpf());
                            //moveAgent(data);
                            //*/
                            break;
                case "NAK":
                            System.out.println("-------------------NAK :(--------- ");
                            //moveAgent(data);
                            break;
                case "ACK":
                            System.out.println("-------------------ACK:D--------- ");
                            dataSensorRequest();
                            //moveACKAgent(data);
                            break;
                    
            }
        } catch (Exception e) {
        }
    }
    
    public void moveAgent(ActionData data) throws InterruptedException{
            AgentState aState= (AgentState)this.getAgent().getState();
            Queue<Motion> possibleMotions=(ArrayDeque<Motion>) aState.getPossibleMotions();
            if(possibleMotions.isEmpty()){
                possibleMotions=generationPossibleMotions(aState);
                if(possibleMotions.isEmpty()){
                    return;
                }
                aState.setPossibleMotions(possibleMotions);
            }
            Motion m=possibleMotions.poll();
            ActionData actionData=new ActionData(m.getXpos(), m.getYpos(), m.getIdfloor(),aState.getAlias(), "move");
            EventBESA event = new EventBESA(UpdateGuard.class.getName(), actionData);
            AgHandlerBESA ah;
            try {
                ah = getAgent().getAdmLocal().getHandlerByAlias("WORLD");
                ah.sendEvent(event);
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
            }
            //System.out.println(actionData);
            
    }
    
    public boolean  intervalValidation(int n,int limit){
        return n>=0&&n<limit;
    }
    public Queue<Motion> generationPossibleMotions(AgentState aState){
            Queue<Motion> possibleMotions=new ArrayDeque<Motion>();
            ModelEdifice edifice=aState.getEdifice();
            int x,y,idFloor;
            x=aState.getXpos();y=aState.getYpos();idFloor=aState.getIdfloor();
            int movX[]={1,0, 0,-1};
            int movY[]={0,1,-1, 0};
            int newX,newY;
            List<Integer> list = new ArrayList<Integer>();
            for (int i=0;i<4;i++){
                   list.add(i);
            }
            //randomized files
            Collections.shuffle(list);
            long seed = System.nanoTime();
            Collections.shuffle(list, new Random(seed));
            Collections.shuffle(list, new Random(seed));
            for(int i:list){
                newX=movX[i]+x;
                newY=movY[i]+y;
                if (intervalValidation(newX,edifice.getWidth())&&intervalValidation(newY,edifice.getLength())&&edifice.getFloor(idFloor).get(newX, newY)=='0'){
                   possibleMotions.add(new Motion(newX, newY, idFloor));
                }
            }
            
            return possibleMotions;
    }

    private void moveACKAgent(ActionData data) throws InterruptedException {
            AgentState aState= (AgentState)this.getAgent().getState();
            //System.out.println(" >>>>>>> "+aState.getXpos()+" - "+aState.getYpos());
            aState.setXpos(data.getXpos());
            aState.setYpos(data.getYpos());
            aState.setIdfloor(data.getIdfloor());
            aState.setPossibleMotions(new ArrayDeque<Motion>() );
            data.setAlias(aState.getAlias());
            data.setAction("move");
            Thread.sleep(1000);
            EventBESA event = new EventBESA(AgentMoveGuard.class.getName(), data);
            AgHandlerBESA ah;
            boolean sw=true;
            do{
                try {
                    ah = getAgent().getAdmLocal().getHandlerByAlias(data.getAlias());
                    ah.sendEvent(event);
                    sw=false;
                } catch (ExceptionBESA e) {
                    ReportBESA.error(e);
                    sw=true;
                }
            }while(sw);
    }
    
    public void dataSensorRequest (){
        AgentProtectorState state= (AgentProtectorState)this.getAgent().getState();
        ActionDataAgent actionData=new ActionDataAgent(state.getSightRange(),state.getRadius(), state.getHeight(), state.getAlias(),"Sensing");
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
    
}
