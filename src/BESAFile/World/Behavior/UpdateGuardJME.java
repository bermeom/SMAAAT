/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.Vector3D;
import BESAFile.World.State.WorldState;
import BESAFile.World.State.WorldStateJME;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class UpdateGuardJME extends GuardBESA{

  @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        ActionData data = (ActionData) ebesa.getData();
        WorldStateJME state = (WorldStateJME) this.getAgent().getState();
        switch (data.getAction()) {
            case "move":
                System.out.println("-------------------Move World:D--------- ");
                moveAgent(data, state);
                break;

        }

    }

    public void moveAgent(ActionData data, WorldStateJME state) {
        System.out.println("==================== "+data.getPosition()+" "+data.getMotion());
        if(state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())!=0){
          ActionDataAgent ad =new ActionDataAgent(data.getType(),data.getAlias(),"NACK");
          Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
          return;
        }
        
        int id=state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos());
        state.getmEdifice().setPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos(), id);
        state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), 0);
        state.getNodeAgent(data.getAlias()).setPoistion(data.getPosition());
        state.getNodeAgent(data.getAlias()).setMotion(data.getMotion());
        state.getNodeAgent(data.getAlias()).setSpeed(data.getSpeed());
        Vector3f believedPosition=getPositionVirtiul(data.getMotion().getIdfloor(),data.getMotion().getYpos(),data.getMotion().getXpos());
        Vector3f position=getPositionVirtiul(data.getPosition().getIdfloor(),data.getPosition().getYpos(),data.getPosition().getXpos());
        System.out.println("############# "+believedPosition+" --- " +position+" "+data.getMotion());
        state.getNodeAgent(data.getAlias()).setBelievedPosition(believedPosition);
        //state.getNodeAgent(data.getAlias()).setBelievedPosition(state.getApp().getPositionVirtiul(data.getIdfloor(),2,2));
        state.getNodeAgent(data.getAlias()).setEnabled(true);
        System.out.println("Print ----------------------------------------------------");
        System.out.println(state.getmEdifice());
        /*Spatial s = (Spatial)state.getNodeAgent(data.getAlias());//state.getApp().getRootNode().getChild(data.getAlias());
        BetterCharacterControl control = s.getControl(BetterCharacterControl.class);
        Vector3f modelForwardDir = new Vector3f((float)data.getDirection().getX(), (float)data.getDirection().getY(), (float)data.getDirection().getZ());
                
        //Vector3f modelForwardDir = state.getApp().getPositionVirtiul(0,5,5);
        float angle = 0;
        if(data.getYpos()==0 && data.getXpos() > 0)
            angle = FastMath.HALF_PI;
        if(data.getYpos()==0 && data.getXpos() < 0)
            angle = -FastMath.HALF_PI;
        if(data.getYpos()<0 && data.getXpos() == 0)
            angle = FastMath.PI; 
        
        modelForwardDir.y=0;
        
        Vector3f walkDirection = new Vector3f(0, 0, 0);
        walkDirection.addLocal(modelForwardDir.normalize().mult(data.getSpeed()));
        control.setWalkDirection(walkDirection);
        
        Quaternion rotateL = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        Vector3f viewDirection = modelForwardDir.normalize();//control.getViewDirection();
        rotateL.multLocal(viewDirection);
        control.setViewDirection(viewDirection);
        
        //*/
        /*
        ActionDataAgent ad =new ActionDataAgent("ACK",data.getAlias(),new Vector3D(viewDirection.x,viewDirection.y, viewDirection.z));
        Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
        //*/
    
    }
    
    
    private Vector3f getPositionVirtiul(int idFloor,int i,int j){
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        return new Vector3f(ws.getApp().getX()+ws.getApp().getWidth()/Const.kGrid-Const.post(i), ws.getApp().getY()-ws.getApp().getDistBetweenFloors()*idFloor+0.23f, ws.getApp().getZ()+ws.getApp().getLength()/Const.kGrid-Const.post(j));
        
    }
}
