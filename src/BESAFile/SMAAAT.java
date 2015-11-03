package BESAFile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Agent.State.AgentStateTest;
import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.PeriodicGuardBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESA.Util.PeriodicDataBESA;
import BESAFile.Data.ActionData;
import BESA.ExceptionBESA;
import BESAFile.World.Behavior.GameGuard;
import BESAFile.World.Behavior.SubscribeGuard;
import BESAFile.World.Behavior.UpdateGuard;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelAgentWorld;
import BESAFile.World.State.WorldState;
import BESAFile.World.WorldAgent;

/**
 *
 * @author berme_000
 */
public class SMAAAT {

    /**
     * @param args the command line arguments
     */
    
    public static int GAME_PERIODIC_TIME = 1000;
    public static int GAME_PERIODIC_DELAY_TIME = 100;
    
    public static void main(String[] args) throws ExceptionBESA {
        // TODO code application logic here
        
        //--------------------------------------------------------------------//
        // Creates and starts the BESA container.                //
        //--------------------------------------------------------------------//
        AdmBESA admLocal = AdmBESA.getInstance();
        //--------------------------------------------------------------------//
        // Creates the display agent.                                     //
        //--------------------------------------------------------------------//        
        
        /*DisplayAgent displayAgent = null;
        String alias = "Display";
        DisplayState displaySate = new DisplayState();
        StructBESA displayStruct = new StructBESA();
        double passwdAg = 0.91;
        */
        
        
        double passwdAg = 0.91;
        // Create World Agent
        
        ModelEdifice edifice=createEdifice();
        WorldState ws=new WorldState(edifice);
        StructBESA wrlStruct = new StructBESA();
        wrlStruct.addBehavior("WorldBehavior");
        wrlStruct.bindGuard("WorldBehavior", GameGuard.class);
        wrlStruct.addBehavior("ChangeBehavior");
        wrlStruct.bindGuard("ChangeBehavior", SubscribeGuard.class);
        wrlStruct.bindGuard("ChangeBehavior", UpdateGuard.class);
        WorldAgent wa = new WorldAgent("WORLD", ws, wrlStruct, passwdAg);
        wa.start();
        
        AgentStateTest a1State = new AgentStateTest(edifice,0,0,0,"a1");
        StructBESA a1Struct = new StructBESA();
        a1Struct.addBehavior("agentMove");
        a1Struct.bindGuard("agentMove", AgentMoveGuard.class);
        //a1Struct.addBehavior("agentPerception");
        //a1Struct.bindGuard("agentPerception", SensorGuard.class);
        Agent agent = new Agent(a1State.getAlias(), a1State, a1Struct, passwdAg);
        agent.start();
        sendEventAgentMove( admLocal,new ActionData(1,1,a1State.getXpos(), a1State.getYpos(), a1State.getIdfloor(), a1State.getAlias(), "move"));
        
        AgentStateTest a2State = new AgentStateTest(edifice,0,3,0,"a2");
        StructBESA a2Struct = new StructBESA();
        a2Struct.addBehavior("agentMove");
        a2Struct.bindGuard("agentMove", AgentMoveGuard.class);
        //a1Struct.addBehavior("agentPerception");
        //a1Struct.bindGuard("agentPerception", SensorGuard.class);
        Agent agent1 = new Agent(a2State.getAlias(), a2State, a2Struct, passwdAg);
        agent1.start();
        sendEventAgentMove( admLocal,new ActionData(1,1,a2State.getXpos(), a2State.getYpos(), a2State.getIdfloor(), a2State.getAlias(), "move"));
        
        
        
        PeriodicDataBESA data  = new PeriodicDataBESA(GAME_PERIODIC_TIME, GAME_PERIODIC_DELAY_TIME, PeriodicGuardBESA.START_PERIODIC_CALL);
        EventBESA startPeriodicEv = new EventBESA(GameGuard.class.getName(), data);
        AgHandlerBESA ah = admLocal.getHandlerByAlias("WORLD");
        ah.sendEvent(startPeriodicEv);
        //*/
        
        
        
        
    }
    
    public static ModelEdifice createEdifice(){
        ModelEdifice edifice=new ModelEdifice(10, 10, 1);
        edifice.setPostGridFloor(0,4 ,0, 'H');
        edifice.setPostGridFloor(0,4 ,1, 'H');
        edifice.setPostGridFloor(0,4 ,2, 'H');
        edifice.setPostGridFloor(0,6 ,9, 'H');
        edifice.setPostGridFloor(0,6 ,8, 'H');
        edifice.setPostGridFloor(0,6 ,7, 'H');
        edifice.setPostGridFloor(0,6 ,6, 'H');
        edifice.setPostGridFloor(0,0 ,6, 'v');
        edifice.setPostGridFloor(0,1 ,6, 'v');
        edifice.setPostGridFloor(0,2 ,6, 'v');
        edifice.setPostGridFloor(0,3 ,6, 'v');
        
        edifice.setPostGridFloor(0,6 ,2, 'X');
        
        edifice.setPostGridFloor(0,9 ,9, 'E');
        
        System.out.println(edifice);
        
        return edifice;
    }

    private static void sendEventAgentMove(AdmBESA admLocal,ActionData actionData) {
        EventBESA event = new EventBESA(AgentMoveGuard.class.getName(), actionData);
        AgHandlerBESA ah;
        boolean sw=true;
        do{
            try {
                ah =AdmBESA.getInstance().getHandlerByAlias(actionData.getAlias());
                ah.sendEvent(event);
                sw=false;
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
                sw=true;
                System.out.println("ERROR");
            }
        }while(sw);
        System.out.println("   -------------------------- Move Agent --------------- ");
    }
    
}
