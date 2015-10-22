/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
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
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class SensorsAgentGuardJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
            ActionDataAgent data = (ActionDataAgent) ebesa.getData();
            //System.out.println("Sensing ->"+data.getAlias()+" <-");
            WorldStateJME state = (WorldStateJME)this.getAgent().getState();
            List<SeenObject> seenObjects=new ArrayList<SeenObject>();
            int movX[]={1,0, 0,-1,1,-1,-1, 1};
            int movY[]={0,1,-1, 0,1,-1, 1,-1};
            int i,j,id;
            ActionDataAgent sod=new ActionDataAgent(data.getType(), data.getAlias(), "NACK");
            
                //Sensor Object
                System.out.println(state.getmEdifice());
                System.out.println(data.getPosition());
                for (int k=0;k<movX.length;k++){
                    for (int q=1;q<=data.getSightRange();q++){
                        i=movX[k]*q+data.getPosition().getXpos();
                        j=movY[k]*q+data.getPosition().getYpos();
                        if (validationPosition(i,state.getmEdifice().getWidth())&&validationPosition(j, state.getmEdifice().getLength())){
                            id=state.getmEdifice().getPostGridFloor(data.getIdfloor(), i, j);
                            //System.out.println( id+" "+new Position(i, j,data.getIdfloor())+" "+state.getListAgents().get(id).getAlias()+"  ** "+state.getListAgents().get(id).getType()+"");
                            if (id > 0){
                                 System.out.println(i+" "+j+" "+ id+" "+state.getListAgents().get(id-1).getAlias()/*+"  ** "+state.getListAgents().get(id).getType()*/+"");
                                 seenObjects.add(new SeenObject(new Position(i, j,data.getIdfloor()), state.getListAgents().get(id-1).getAlias(), state.getListAgents().get(id-1).getType()));
                            }else if(id < 0){
                                    System.out.println("========="+data.getIdfloor());
                                    seenObjects.add(new SeenObject(new Position(i, j,data.getIdfloor()), "B", -1));
                            }
                        }
                    }
                }
            try {
                //Sensor FLOOR Node floor=state.getApp().getWallsFloors().get(data.getIdfloor());
                //System.out.println("------------------ EXIT  ---------------");
                //*/
                sod = new ActionDataAgent(data.getAlias(),data.getType(),seenObjects,data.getPosition(),"ACK_SENSOR");
            } catch (Exception e) {
                System.out.println("ERREOR Sensing ->"+data.getAlias()+" <- "+e);
            }
            Agent.sendMessage(Const.getGuardMove(data.getType()), data.getAlias(), sod );
    }

    

    private void print(List<SeenObject> seenObjects) {
        for(SeenObject so:seenObjects){
            System.out.println(so);
        }
    }
    
    private boolean validationPosition(int i, int lim) {
        return i >= 0 && i < lim;
    }
    
}
