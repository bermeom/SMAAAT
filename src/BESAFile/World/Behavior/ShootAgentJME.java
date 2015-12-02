/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Behavior;

import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.GuardBESA;
import BESAFile.Agent.Agent;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.ShootAgentDataJME;
import BESAFile.World.State.WorldStateJME;
import BESAFile.World.WorldAgentJME;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Controls.BulletControl;
import simulation.Controls.PositionController;
import simulation.SmaaatApp;
import simulation.utils.Const;
import simulation.utils.Utils;

/**
 *
 * @author berme_000
 */
public class ShootAgentJME extends GuardBESA{

    @Override
    public void funcExecGuard(EventBESA ebesa) {
        //ShootAgentDataJME
        
       
        
        ShootAgentDataJME data = (ShootAgentDataJME) ebesa.getData();
        WorldStateJME state = (WorldStateJME) this.getAgent().getState();
        int id=state.getmEdifice().getPostGridFloor(0,data.getPosition().getXpos(), data.getPosition().getYpos());
        if(id<=0){
            return;
        }
        
        PositionController pc=state.getAgentController(id-1);    
        Sphere bullet;
        float bulletRadius = ((float) pc.getRadius()) / 5;
        float bulletSpeed=20;
        
        Geometry ball_geo = createShooting(bulletRadius, state.getApp(),data.getType());
        
        Vector3f position = pc.getNode().getWorldTranslation().clone();
        Vector3f believedTarget=Const.getPositionVirtiul(data.getTarget().getIdfloor(),data.getTarget().getXpos(),data.getTarget().getYpos(),state.getmEdifice().getLength(),state.getmEdifice().getWidth(),state.getApp().getX(),state.getApp().getY(),state.getApp().getZ(),state.getApp().getDistBetweenFloors());
        Vector3f direction = new Vector3f(believedTarget.x-position.x, 0, believedTarget.z-position.z).normalize();
        position.setY(position.getY()+(float)pc.getRadius());
        //change view
   
        pc.setViewDirection(direction);

        /*Ajuste de posicion para que la bala este fuera de la geometria del agente*/
        position.addLocal(direction.mult((float)pc.getRadius() + (bulletRadius*2f )));
        ball_geo.setLocalTranslation(position);

        BulletControl ball_phy = new BulletControl(1f);
        ball_geo.addControl(ball_phy);
        ball_phy.setLinearVelocity(direction.mult(bulletSpeed));
        ball_phy.setFriction(0.1f);
        ball_phy.setGravity(Vector3f.ZERO.setY(-0.0001f));
    
       addShoot(ball_geo, ball_phy, state.getApp());
        int shootingRestTime = Utils.randomInteger(100, 300);
        try {
            Thread.sleep(shootingRestTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(ShootAgentJME.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Replay
        int reply_with=data.getIn_reply_to();
        int in_reply_to=data.getReply_with();
        ActionDataAgent actionData=new ActionDataAgent(reply_with,in_reply_to,data.getAlias(),"ACK_SHOOT");
        Agent.sendMessage((Const.getGuardMove(data.getType())), data.getAlias(), actionData);
    }
    
    private void addShoot(final Geometry ball_geo,final BulletControl ball_phy,final SmaaatApp app ){
        
        WorldAgentJME wAgent = ((WorldAgentJME) agent);
        
        wAgent.execAsyncInWorldThread(new Callable<Void>() {
                public Void call() throws Exception {
                    app.getBulletsNode().attachChild(ball_geo);
                    app.getPhysicsSpace().add(ball_phy);
                    return null;
                }
            });
        
    }
    
    private Geometry createShooting(float bulletRadius, SmaaatApp app,int type) {
       
        Sphere bullet=new Sphere(4, 4, bulletRadius);
        Material bulletMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        switch(type){
            case(1):bulletMaterial.setColor("Color", ColorRGBA.Blue);break;
            case(4):bulletMaterial.setColor("Color", ColorRGBA.Red);break;
                
        }
        
        Geometry ball_geo = new Geometry(Const.Bullet, bullet);
        ball_geo.setMaterial(bulletMaterial);
        return ball_geo;
    }
    

    
}
