/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.State.Motion;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.AddAgentDataJME;
import BESAFile.Data.ChangeFloorData;
import BESAFile.Data.ShutdownAgentData;
import BESAFile.Data.Vector3D;
import BESAFile.World.State.WorldStateJME;
import BESAFile.World.WorldAgentJME;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Controls.PositionController;
import simulation.SmaaatApp;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class ShutdownAgentGuardJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        ShutdownAgentData data=(ShutdownAgentData) ebesa.getData();
      try {
               
                
                
                int id=ws.getmEdifice().getPostGridFloor(0,data.getPosition().getXpos(), data.getPosition().getYpos());
                /*if (id<=0 || !ws.getListAgents().get(id-1).getAlias().equals(data.getAlias())&&(data.getTypeChange()==id)){
                    System.out.println("ERROR CHANGE FLOOR NACK 1 "+ data.getAlias());
                    ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),"NACK_CHANGE_FLOOR",data.getAlias(),data.getPosition());
                    Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
                    return;
                }*/
                PositionController pc=ws.getAgentController(id-1);
                pc.setEnabled(false);
                ws.getmEdifice().setPostGridFloor(0, data.getPosition().getXpos(), data.getPosition().getYpos(), ws.getIdGridPAgent(id-1));
                
                if(pc.getData()!=null){
                    Motion m=pc.getMotion();
                    if(ws.getmEdifice().getPostGridFloor(0, m.getXpos(), m.getYpos())==id){
                        ws.getmEdifice().setPostGridFloor(0, m.getXpos(), m.getYpos(), ws.getIdGridMAgent(id-1));
                
                    }
                }
                //AdmBESA.getInstance(data.getAlias()).kill(Const.passwdAg);
                removeAgentJME(pc.getNode(), ws.getApp());
                //System.out.println(ws.getmEdifice());
                
                
        } catch (Exception e) {
                System.out.println("Error:"+e);
        }
      
      boolean sw=false;
        //do{
            try {
                 Thread.sleep(100);
                AgHandlerBESA ah = AdmBESA.getInstance().getHandlerByAlias(data.getAlias());
                AdmBESA.getInstance().killAgent(ah.getAgId(),Const.passwdAg);
                sw=true;
            } catch (ExceptionBESA ex) {
                Logger.getLogger(ShutdownAgentGuardJME.class.getName()).log(Level.SEVERE, null, ex);
                sw=false;
           } catch (InterruptedException ex) {
            Logger.getLogger(ShutdownAgentGuardJME.class.getName()).log(Level.SEVERE, null, ex);
        }
        //}while(!sw);
    }
    
    private void removeAgentJME(final Node node,final SmaaatApp app ){
        
        WorldAgentJME wAgent = ((WorldAgentJME) agent);
        
        wAgent.execAsyncInWorldThread(new Callable<Void>() {
                public Void call() throws Exception {
                    node.removeFromParent();
                    app.getPhysicsSpace().remove(node.getControl(BetterCharacterControl.class));
                    return null;
                }
            });
        
    }
    
}
