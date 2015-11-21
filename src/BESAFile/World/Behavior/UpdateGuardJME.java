/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.DataBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Agent.State.AgentState;
import BESAFile.Agent.State.Position;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
                moveAgent(data, state);
                break;
            case "moveACK":
                moveACK(data, state);
                break;
            case "moveNACK":
                moveNACK(data, state);
                break;
            case "disableController":
                disableController(data, state);
                break;
                

        }

    }
    
    private void moveAgent(ActionData data, WorldStateJME state) {
        //try {
            int reply_with=data.getIn_reply_to();
            int in_reply_to=data.getReply_with();
            ReportBESA.info("-------------------Move World:D--------- "+data.getAlias());
            
            int id=state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos());
            //System.out.println("<><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+ id);
            
            if (id<=0 || !state.getListAgents().get(id-1).getAlias().equals(data.getAlias())){
                ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),"NACK",data.getAlias(),data.getPosition());
                Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
                return;
                }else if (state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())!=0){
                         ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),"NACK",data.getAlias(),data.getPosition());
                         Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
                         return;
                         }else if (!state.moveAgent(data.getPosition(), data.getMotion(), id-1)){
                                    ActionDataAgent ad =new ActionDataAgent(reply_with,in_reply_to,data.getType(),"NACK",data.getAlias(),data.getPosition());
                                    //Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(), ad);
                                    return;
                                    }
            
            data.setId(id);
            state.getmEdifice().setPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos(), id);
            state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), id);
            PositionController pc=state.getAgentController(data.getAlias());
            pc.setReply_with(reply_with);
            pc.setIn_reply_to(in_reply_to);
            pc.setPoistion(data.getPosition());
            pc.setMotion(data.getMotion());
            pc.setSpeed(data.getSpeed());
            pc.setData(data);
            Vector3f believedPosition=getPositionVirtiul(data.getMotion().getIdfloor(),data.getMotion().getYpos(),data.getMotion().getXpos());
            pc.setBelievedPosition(believedPosition);
            //System.out.println(data.getAlias()+" "+data.getPosition());
            //System.out.println(state.getmEdifice());
            pc.setEnabled(true);
       /* } catch (Exception e) {
              ReportBESA.info("xxxxxxxxxxxxxxxxxxx ERROR UPDATE MOVE xxxxxxxxxxxxxxxxxxxxxxxxxxx"+e );
              
                  
        }*/
 
        
    
    }
    
     public void moveACK(ActionData data, WorldStateJME state) {
         boolean sw=false; 
         //do{
            try {
                   //ReportBESA.info("-------------------+++++++++++  MoveACK World:D--------- "+data.getAlias()+" "+state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())+" == "+data.getId());
                   if (state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos())==data.getId()){
                       //System.out.println("----------------------------+++++++++++++++++++++++++++++++ PASO1 "+data.getAlias());
                       state.getmEdifice().setPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos(), 0);
                   }
                   if (state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())==data.getId()){
                       //System.out.println("----------------------------+++++++++++++++++++++++++++++++ PASO2 "+data.getAlias());
                       ActionDataAgent ad =new ActionDataAgent(data.getIn_reply_to(),data.getReply_with(),data.getType(),"ACK",data.getAlias(),new Position(data.getMotion().getXpos(), data.getMotion().getYpos(), data.getMotion().getIdfloor()));
                       Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(),ad);
                   }
                   //System.out.println(state.getmEdifice());
                   sw=true;
            } catch (Exception e) {
                    ReportBESA.info("xxxxxxxxxxxxxxxxxxx ERROR UPDATE MOVE ACKxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    sw=false;
               }
         //}while(!sw);
     }
     
      public void moveNACK(ActionData data, WorldStateJME state) {
          boolean sw=false;
          do{
            try {
                  //ReportBESA.info("-------------------+++++++++++  MoveNACK World:D--------- "+data.getAlias());


                  if (state.getmEdifice().getPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos())==data.getId()){
                      //System.out.println("----------------------------+++++++++++++++++++++++++++++++ PASO1 "+data.getAlias());
                      state.getmEdifice().setPostGridFloor(data.getMotion().getIdfloor(),data.getMotion().getXpos(), data.getMotion().getYpos(), 0);
                  }
                  if (state.getmEdifice().getPostGridFloor(data.getPosition().getIdfloor(),data.getPosition().getXpos(), data.getPosition().getYpos())==data.getId()){
                      //System.out.println("----------------------------+++++++++++++++++++++++++++++++ PASO2 "+data.getAlias());
                      state.getListAgents().get(data.getId()-1).setPosition(data.getPosition());
                      ActionDataAgent ad =new ActionDataAgent(data.getIn_reply_to(),data.getReply_with(),data.getType(),"NACK",data.getAlias(),data.getPosition());
                      Agent.sendMessage(Const.getGuardMove(data.getType()),data.getAlias(),ad);
                  }

                  sw=true;
              } catch (Exception e) {
                   ReportBESA.info("xxxxxxxxxxxxxxxxxxx ERROR UPDATE MOVE NACK xxxxxxxxxxxxxxxxxxxxxxxxxxx");
                   sw=false;
              }
          }while(!sw);
            
      }
    
     private Vector3f getPositionVirtiul(int idFloor,int i,int j){
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        return new Vector3f(ws.getApp().getX()+ws.getApp().getLength()/Const.kGrid-Const.post(i), ws.getApp().getY()-ws.getApp().getDistBetweenFloors()*idFloor+0.23f, ws.getApp().getZ()+ws.getApp().getWidth()/Const.kGrid-Const.post(j));
        
    }

    private void disableController(ActionData data, WorldStateJME state) {
        PositionController pc=state.getAgentController(data.getAlias());
        if (pc != null){
            pc.setEnabled(false);    
        }
        
    }
    
    
  
}
