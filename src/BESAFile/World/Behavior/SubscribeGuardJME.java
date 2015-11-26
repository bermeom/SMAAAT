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
import BESAFile.Agent.Behavior.SubscribeResponseGuard;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.SubscribeDataJME;
import BESAFile.Data.Vector3D;
import BESAFile.World.State.WorldStateJME;
import BESAFile.World.WorldAgentJME;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.concurrent.Callable;
import simulation.Controls.PositionController;
import simulation.SmaaatApp;
import simulation.utils.Const;

/**
 *
 * @author berme_000
 */
public class SubscribeGuardJME extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        SubscribeDataJME data = (SubscribeDataJME)ebesa.getData();
        try {
            WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
            Spatial model=createSpatialGeometry(data.getAlias(),(float)data.getRadius(),(float)data.getHeight(),1);
            Vector3f position=Const.getPositionVirtiul(data.getIdfloor(), data.getXpos(), data.getYpos(),ws.getmEdifice().getLength(),ws.getmEdifice().getWidth(),ws.getApp().getX(),ws.getApp().getY(),ws.getApp().getZ(),ws.getApp().getDistBetweenFloors());
            Vector3f direction=new Vector3f((float)data.getDirection().getX(),(float) data.getDirection().getY(),(float) data.getDirection().getZ());
            switch(data.getType()){
                case(1): model=createModelProtector();  break;
                case(2): model=createModelExplorer();  break;
                case(3): model=createModelHostage();  break;
                case(4): model=createModelEnemy();  break;
                
            };
            
            Node node = new Node(data.getAlias());
            node.setLocalTranslation(position);
            node.attachChild(model);
            
            //Bullet
            BetterCharacterControl physicsCharacter = new BetterCharacterControl((float)data.getRadius(),(float)data.getHeight() , 15.0f);
            node.addControl(physicsCharacter);
            //ws.getApp().getPhysicsSpace().add(physicsCharacter);
            physicsCharacter.setViewDirection(direction);
            //*/
            //ws.getApp().getCharacterNode().attachChild(node); 
            subcribeInApp(physicsCharacter, node, ws.getApp());
            PositionController controller=new PositionController(node,direction,position,data.getAlias(),data.getType(),0,data.getRadius(),data.getHeight(),new Position(data.getXpos(), data.getYpos(),data.getIdfloor()) );
            controller.setEnabled(false);
            ws.addAgent(data.getAlias(),controller,data);
            node.addControl(controller);
            //answer(true, data.getAlias());
        } catch (Exception e) {
            ReportBESA.error(e);
            answer(false, data.getAlias());
        }
    
    }
    
    private void subcribeInApp(final BetterCharacterControl physicsCharacter,final Node node,final SmaaatApp app ){
        
        WorldAgentJME wAgent = ((WorldAgentJME) agent);
        
        wAgent.execAsyncInWorldThread(new Callable<Void>() {
                public Void call() throws Exception {
                    app.getPhysicsSpace().add(physicsCharacter);
                    app.getCharacterNode().attachChild(node); 
                    return null;
                }
            });
        
    }
    
    private Spatial createModelProtector(){
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        Spatial machineSpatial = ws.getApp().getAssetManager().loadModel("Models/AgentProtector/ED-209.j3o");
        machineSpatial.scale(.18f);
        //machineSpatial.rotate(0, FastMath.PI, 0);
        machineSpatial.setLocalTranslation(0, 0, 0.2f);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    private Spatial createModelExplorer(){
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        Spatial machineSpatial = ws.getApp().getAssetManager().loadModel("Models/AgentExplorer/Drone/Drone.j3o");
        machineSpatial.scale(.15f);
        //machineSpatial.rotate(0, 2*FastMath.PI, 0);
        machineSpatial.setLocalTranslation(0f, 1f, 0);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    private Spatial createModelHostage(){
        
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        Spatial machineSpatial = ws.getApp().getAssetManager().loadModel("Models/AgentHostage/Android/android.j3o");
        machineSpatial.scale(0.25f);
        //machineSpatial.scale(.15f);
        //machineSpatial.rotate(0, -FastMath.PI/2, 0);
        machineSpatial.setLocalTranslation(0, 0.5f, 0);
        Material mat1 = new Material(ws.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Green);
        machineSpatial.setMaterial(mat1);
        
        return machineSpatial;
    }
    
    private Spatial createModelEnemy(){
        try {
            WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
            Spatial machineSpatial = ws.getApp().getAssetManager().loadModel("Models/AgentEnemy/Falkenmorder ver2.j3o");
            machineSpatial.scale(.06f);
            //machineSpatial.scale(.15f);
            machineSpatial.rotate(0, -FastMath.PI/2, 0);
            machineSpatial.setLocalTranslation(0, 0.5f, 0);
            //machineSpatial.setMaterial(mat1);
            //*/
            return machineSpatial;
        } catch (Exception e) {
        
        }
        
        
        return createModelProtector();
    }
    
 
    
    
    
    protected Geometry createSpatialGeometry(String name,float radius,float height,int type) {
        WorldStateJME ws = (WorldStateJME)this.getAgent().getState();
        Sphere s = new Sphere(10, 10, radius);
        Geometry g = new Geometry(name, s);
        Material mat = new Material(ws.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(mat);
        g.setLocalTranslation(0, height / 2, 0);
        return g;
    }
 
     public void answer(boolean ack,String alias){
        int reply_with=1;
        int in_reply_to=-1;
        ActionDataAgent actionData=new ActionDataAgent(reply_with,in_reply_to,alias,"NAK");
        if (ack){
            actionData.setAction("ACK");
        }
        EventBESA event = new EventBESA(SubscribeResponseGuard.class.getName(), actionData);
        AgHandlerBESA ah;
        try {
            ah = getAgent().getAdmLocal().getHandlerByAlias(alias);
            ah.sendEvent(event);
        } catch (ExceptionBESA e) {
            ReportBESA.error(e);
        }    
    
    }
}
