/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import BESAFile.World.State.WorldStateJME;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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
            state.getApp().getCharacterNode();
            try {
                //Sensor Object
                List<Spatial> characters = state.getApp().getCharacterNode().getChildren();
                Node node=(Node)state.getApp().getCharacterNode().getChild(data.getAlias());
                Vector3f position = node.getWorldTranslation().clone();
                System.out.println("Sensing ->"+characters.size()+" <-");
                if (characters != null && characters.size() > 1) {
                    for (Spatial s : characters) {
                        //System.out.print("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <-");
                        if (!s.equals(node) && !s.getName().toLowerCase().contains("debug")) {
                            float distance = node.getWorldTranslation().distance(s.getWorldTranslation());
                            //System.out.print("NAME Sensing ->"+s.getName()+" "+s.getName().toLowerCase()+" <-");
                            if (distance <= data.getSightRange()) {
                                //System.out.println("OK "+s.getWorldTranslation()+" "+position);
                                position.setY(position.getY()+(float) (data.getHeight()/2));
                                Vector3f direction = s.getWorldTranslation().subtract(node.getWorldTranslation());
                                position.addLocal(direction.normalize().mult((float)data.getRadius() + ((float)data.getRadius() * 0.1f)));
                                Ray r = new Ray(position, direction.normalize());
                                r.setLimit(data.getSightRange());
                                CollisionResults results = new CollisionResults();
                                state.getApp().getCharacterNode().collideWith(r, results);
                                if (results.size() > 0) {
                                    //System.out.println("+");
                                    CollisionResult cr = results.getClosestCollision();
                                    String name = cr.getGeometry().getParent().getName();
                                    //System.out.println("Name"+name);
                                    if (name != null && !name.equals("floor") && !name.toLowerCase().contains("debug")) {
                                        seenObjects.add(new SeenObject(new Vector3D(((Node)s).getLocalTranslation().x, ((Node)s).getLocalTranslation().y, ((Node)s).getLocalTranslation().z),s.getName(), getType(s.getName())));
                                    }
                                }
                            }
                        }
                    }
                }
                print(seenObjects);
                //*/
                //Sensor FLOOR
                
                
                
                
                
                //*/
            
            } catch (Exception e) {
                System.out.println("ERREOR Sensing ->"+data.getAlias()+" <-");
            }
            
    }

    private int getType(String name) {
        if(name.contains(Const.GuardianAgent)){
            return 1;
        }
        if(name.contains(Const.ExplorerAgent)){
            return 2;
        }
        if(name.contains(Const.HostageAgent)){
            return 3;
        }
        if(name.contains(Const.EnemyAgent)){
            return 4;
        }
        return 0;
    }

    private void print(List<SeenObject> seenObjects) {
        for(SeenObject so:seenObjects){
            System.out.println(so);
        }
    }
    
    private void DetectionWalls(Node agent,Vector3f position,ActionDataAgent data){
        
    
    }
    
    
}
