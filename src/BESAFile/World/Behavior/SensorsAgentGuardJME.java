/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentProtectorMoveGuard;
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
            System.out.println("Sensing ->"+data.getAlias()+" <-");
            WorldStateJME state = (WorldStateJME)this.getAgent().getState();
            List<SeenObject> seenObjects=new ArrayList<SeenObject>();
            List<SeenWall> seenWalls=new ArrayList<SeenWall>();
            Vector3f position ;
            ActionDataAgent sod=new ActionDataAgent(data.getType(), data.getAlias(), "NACK_Sensor");
            try {
                //Sensor Object
                Node agent=(Node)state.getApp().getCharacterNode().getChild(data.getAlias());
                position = agent.getWorldTranslation().clone();
                seenObjects=searchObjectsRangeSightRange(agent, position,data,state.getApp().getCharacterNode());
                //Sensor FLOOR Node floor=state.getApp().getWallsFloors().get(data.getIdfloor());
                seenWalls=searchWallsRangeSightRange(agent, position,data,state.getApp().getWallsFloors().get(data.getIdfloor()));
                System.out.println("------------------ EXIT  ---------------");
                //*/
                position= agent.getWorldTranslation().clone();
                sod = new ActionDataAgent(data.getAlias(),data.getType(),seenObjects,seenWalls,new Vector3D((double)position.x, (double)position.y, (double)position.z),"ACK_SENSOR");
            
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
    
    private List<SeenObject> searchObjectsRangeSightRange(Node agent,Vector3f position,ActionDataAgent data,Node root){
            List<SeenObject> seenObjects=new ArrayList<SeenObject>();
            List<Spatial> characters = root.getChildren();
            //System.out.println("Entro ->"+data.getAlias()+" Sensing ->"+characters.size()+" <-");
            if (characters != null && characters.size() > 1) {
                for (Spatial s : characters) {
                    //System.out.print("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <-");
                    if (!s.equals(agent) && !s.getName().toLowerCase().contains("debug")) {
                        float distance = agent.getWorldTranslation().distance(s.getWorldTranslation());
                        //System.out.println("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <- "+distance+" "+data.getSightRange());
                        if (distance <= data.getSightRange()) {
                            //System.out.println("OK "+s.getWorldTranslation()+" "+position);
                            position.setY(position.getY()+(float) (data.getHeight()/2));
                            Vector3f direction = s.getWorldTranslation().subtract(position);
                            position.addLocal(direction.normalize().mult((float)data.getRadius() + ((float)data.getRadius() * 0.1f)));
                            Ray r = new Ray(position, direction.normalize());
                            r.setLimit(data.getSightRange());
                            CollisionResults results = new CollisionResults();
                            root.collideWith(r, results);
                            if (results.size() > 0) {
                                CollisionResult cr = results.getClosestCollision();
                                String name = cr.getGeometry().getParent().getName();
                                //System.out.println("+++++++ Name : "+name);
                                if (name.equals(s.getName())) {
                                    Vector3D positionS=new Vector3D(s.getLocalTranslation().x, s.getLocalTranslation().y, s.getLocalTranslation().z);
                                    seenObjects.add(new SeenObject(positionS,name, Const.getType(s.getName())));
                                }
                            }
                        }
                    }
                }
            }
            print(seenObjects);
            return seenObjects;
    }
    
    private List<SeenWall> searchWallsRangeSightRange(Node agent,Vector3f position,ActionDataAgent data,Node root){
            List<SeenWall> seenWalls=new ArrayList<SeenWall>();
            List<Spatial> characters = root.getChildren();
            //System.out.println("Entro ->"+data.getAlias()+" Sensing ->"+characters.size()+" <-");
            if (characters != null && characters.size() > 1) {
                for (Spatial s : characters) {
                    //System.out.print("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <-");
                    if (!s.equals(agent) && !s.getName().toLowerCase().contains("debug")) {
                        float distance = agent.getWorldTranslation().distance(s.getWorldTranslation());
                        //System.out.println("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <- "+distance+" "+data.getSightRange());
                        if (distance <= data.getSightRange()) {
                            
                            position.setY(position.getY()+(float) (data.getHeight()/2));
                            Vector3f direction = s.getWorldTranslation().subtract(position);
                            position.addLocal(direction.normalize().mult((float)data.getRadius() + ((float)data.getRadius() * 0.5f)));
                            Ray r = new Ray(position, direction.normalize());
                            //System.out.println("OK "+position+" "+direction);
                            //*/
                            
                            r.setLimit(data.getSightRange());
                            CollisionResults results = new CollisionResults();
                            root.collideWith(r, results);
                            if (results.size() > 0) {
                                CollisionResult cr = results.getClosestCollision();
                                String name = cr.getGeometry().getParent().getName();
                                //System.out.println("+++++++ Name : "+name+" "+results.size());
                                if (name.equals("Walls"+data.getIdfloor())) {
                                    Vector3D positionS=new Vector3D(s.getLocalTranslation().x, s.getLocalTranslation().y, s.getLocalTranslation().z);
                                    seenWalls.add(new SeenWall(positionS,s.getName(), Const.getType(s.getName())));
                                }
                            }
                        }
                    }
                }
            }
            /*
            for(SeenWall so:seenWalls){
                System.out.println(so.getName()+" "+so);
            }*/
            return seenWalls;
    
    }
    
}
