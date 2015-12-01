/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.Agent.PeriodicGuardBESA;
import BESAFile.Agent.Agent;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.AddAgentDataJME;
import BESAFile.Data.ChangeFloorData;
import BESAFile.Data.Vector3D;
import BESAFile.World.State.WorldStateJME;
import BESAFile.World.WorldAgentJME;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.AbstractPhysicsControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Transform;
import com.jme3.scene.Node;
import java.util.concurrent.Callable;
import simulation.Controls.BulletControl;
import simulation.Controls.PositionController;
import simulation.SmaaatApp;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class ChangeFloorGuardJME  extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
          try {
               
                WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
                ChangeFloorData data=(ChangeFloorData) ebesa.getData();
                int reply_with=data.getIn_reply_to();
                int in_reply_to=data.getReply_with();
                
                int id=ws.getmEdifice().getPostGridFloor(0,data.getPosition().getXpos(), data.getPosition().getYpos());
                
                if (id<=0 || !ws.getListAgents().get(id-1).getAlias().equals(data.getAlias())&&(data.getTypeChange()==id)){
                    System.out.println("ERROR CHANGE FLOOR NACK 1 "+ data.getAlias());
                    ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),"NACK_CHANGE_FLOOR",data.getAlias(),data.getPosition());
                    Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
                    return;
                }
                if (data.getTypeChange()==-3){
                    System.out.println("LLego-------------------"+data.getType());
                    PositionController pc=ws.getAgentController(id-1);
                    AddAgentDataJME dataJME=new AddAgentDataJME(reply_with,in_reply_to,data.getPosition().getXpos(), data.getPosition().getYpos(), data.getPosition().getIdfloor()+1, data.getAlias(), new Vector3D(0, 0, 1), pc.getType(), pc.getRadius(), pc.getHeight());
                    pc.setEnabled(false);
                    removeAgentJME(pc.getNode(), ws.getApp());
                    ws.getmEdifice().setPostGridFloor(0, data.getPosition().getXpos(), data.getPosition().getYpos(), ws.getIdGridPAgent(id-1));
                    Agent.sendMessage(AddAgenteFloorGuardJME.class, Const.World+(ws.getIdFloor()+1), dataJME);
                    System.out.println(ws.getmEdifice());
                }else if (data.getTypeChange()==-4){
                    System.out.println("LLego-------------------"+data.getType());
                    PositionController pc=ws.getAgentController(id-1);
                    AddAgentDataJME dataJME=new AddAgentDataJME(reply_with,in_reply_to,data.getPosition().getXpos(), data.getPosition().getYpos(), data.getPosition().getIdfloor()-1, data.getAlias(), new Vector3D(0, 0, 1), pc.getType(), pc.getRadius(), pc.getHeight());
                    pc.setEnabled(false);
                    removeAgentJME(pc.getNode(), ws.getApp());
                    ws.getmEdifice().setPostGridFloor(0, data.getPosition().getXpos(), data.getPosition().getYpos(), ws.getIdGridPAgent(id-1));
                    Agent.sendMessage(AddAgenteFloorGuardJME.class, Const.World+(ws.getIdFloor()-1), dataJME);
                    System.out.println(ws.getmEdifice());
                }
                
        } catch (Exception e) {

        }
    
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
