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
import BESAFile.Agent.State.AgentState;
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
import simulation.Controls.PositionController;
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
        if (data==null){
            return;
        }
        switch (data.getAction()) {
            case "move":
                //ReportBESA.info("-------------------Move World:D--------- "+data.getAlias());
                moveAgent(data, state);
                break;
            case "moveACK":
                ReportBESA.info("-------------------+++++++++++  MoveACK World:D--------- "+data.getAlias());
                if (state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos())==data.getId()){
                    state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), 0);
                }
                break;
            case "moveNACK":
                ReportBESA.info("-------------------+++++++++++  MoveACK World:D--------- "+data.getAlias());
                if (state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos())==data.getId()){
                    state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), -data.getId());
                    state.getmEdifice().setPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos(), 0);
            
                }
                break;

        }

    }

    public void moveAgent(ActionData data, WorldStateJME state) {
        try {
            int reply_with=data.getIn_reply_to();
            int in_reply_to=data.getReply_with();
              
            if(state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())!=0){
              ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),data.getAlias(),"NACK");
              Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
              return;
            }
            
            int id=state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos());
            data.setId(-id);
            state.getmEdifice().setPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos(), id);
            state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), -id);
            PositionController pc=state.getAgentController(data.getAlias());
            pc.setReply_with(reply_with);
            pc.setIn_reply_to(in_reply_to);
            pc.setPoistion(data.getPosition());
            pc.setMotion(data.getMotion());
            pc.setSpeed(data.getSpeed());
            pc.setData(data);
            Vector3f believedPosition=getPositionVirtiul(data.getMotion().getIdfloor(),data.getMotion().getYpos(),data.getMotion().getXpos());
            Vector3f position=getPositionVirtiul(data.getPosition().getIdfloor(),data.getPosition().getYpos(),data.getPosition().getXpos());
            pc.setBelievedPosition(believedPosition);
            //System.out.println(data.getAlias()+" "+data.getPosition());
            //System.out.println(state.getmEdifice());
            pc.setEnabled(true);
            
        } catch (Exception e) {
              ReportBESA.info("xxxxxxxxxxxxxxxxxxx ERROR UPDATE xxxxxxxxxxxxxxxxxxxxxxxxxxx");
                  
        }
 
        
    
    }
    
    
    private Vector3f getPositionVirtiul(int idFloor,int i,int j){
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        return new Vector3f(ws.getApp().getX()+ws.getApp().getWidth()/Const.kGrid-Const.post(i), ws.getApp().getY()-ws.getApp().getDistBetweenFloors()*idFloor+0.23f, ws.getApp().getZ()+ws.getApp().getLength()/Const.kGrid-Const.post(j));
        
    }
}
