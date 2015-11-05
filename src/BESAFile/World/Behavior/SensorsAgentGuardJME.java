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
import BESAFile.Agent.Behavior.AgentProtectorMoveGuard;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import BESAFile.Model.SeenWall;
import BESAFile.World.State.WorldStateJME;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.elements.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Controls.PositionController;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class SensorsAgentGuardJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            int reply_with=data.getIn_reply_to();
            int in_reply_to=data.getReply_with();
            try {
                //System.out.println("Sensing ->"+data.getAlias()+" <-");
                WorldStateJME state = (WorldStateJME)this.getAgent().getState();
                List<SeenObject> seenObjects=new ArrayList<SeenObject>();
                int movX[]={1,0, 0,-1,1,-1,-1, 1};
                int movY[]={0,1,-1, 0,1,-1, 1,-1};
                int i,j,id;
                //Sensor Object
                for (int k=0;k<movX.length;k++){
                    for (int q=1;q<=data.getSightRange();q++){
                        i=movX[k]*q+data.getPosition().getXpos();
                        j=movY[k]*q+data.getPosition().getYpos();
                        if (validationPosition(i,state.getmEdifice().getWidth())&&validationPosition(j, state.getmEdifice().getLength())){
                            id=state.getmEdifice().getPostGridFloor(data.getIdfloor(), i, j);
                            if (id > 0){
                                 seenObjects.add(new SeenObject(new Position(i, j,data.getIdfloor()), state.getListAgents().get(id-1).getAlias(), state.getListAgents().get(id-1).getType()));
                                 break;
                            }else if(id < 0){
                                    seenObjects.add(new SeenObject(new Position(i, j,data.getIdfloor()), "B", -1));
                                    break;
                            }
                        }
                    }
                }
                //Sensor FLOOR Node floor=state.getApp().getWallsFloors().get(data.getIdfloor());
                //*/
                ActionDataAgent sod = new ActionDataAgent(reply_with,in_reply_to,data.getAlias(),data.getType(),seenObjects,data.getPosition(),"ACK_SENSOR");
                sendMessage(Const.getGuardMove(data.getType()), data.getAlias(), sod );
                //System.out.println("------------------ EXIT  ---------------"+data.getAlias());
                
            } catch (Exception e) {
                System.out.println(" xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  ERREOR Sensing ->"+data.getAlias()+" <- "+e);
                ActionDataAgent sod=new ActionDataAgent(reply_with,in_reply_to,data.getType(), data.getAlias(), "NACK");
                sendMessage(Const.getGuardMove(data.getType()), data.getAlias(), sod );
            }
            
    }

    

    private void print(List<SeenObject> seenObjects) {
        for(SeenObject so:seenObjects){
            System.out.println(so);
        }
    }
    
    private boolean validationPosition(int i, int lim) {
        return i >= 0 && i < lim;
    }
    
    private void sendMessage(Class guard, String alias, DataBESA data) {
        try {
            EventBESA ev = new EventBESA(guard.getName(), data);
            AgHandlerBESA ah = AdmBESA.getInstance().getHandlerByAlias(alias);
            ah.sendEvent(ev);
        } catch (ExceptionBESA ex) {
            ReportBESA.info("xxxxxxxxxxxxxxxxxxx ERROR SEND SENDOR FATAL !! xxxxxxxxxxxxxxxxxxxxxxxxxxx");
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
