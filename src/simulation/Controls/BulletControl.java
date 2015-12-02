package simulation.Controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;

public class BulletControl extends RigidBodyControl implements PhysicsCollisionListener {

    private boolean hasCollide = false;

    public BulletControl(float mass) {
        super(mass);
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        if (space != null) {
            space.addCollisionListener(this);
        }
    }

    public void collision(PhysicsCollisionEvent event) {

        if (space == null) {
            return;
        }
        if (event.getObjectA() == this || event.getObjectB() == this) {
            String nameNode="";
            this.setLinearVelocity(this.getLinearVelocity().mult(0));
            if(event.getObjectA() == this){
                nameNode=event.getObjectB().getUserObject()+"";
            }else{
                nameNode=event.getObjectA().getUserObject()+"";
            }
            nameNode=nameNode.split(" ")[0];
            System.out.println("->>>>>>>>>>"+nameNode);
            hasCollide = true;
           
            
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (hasCollide) {
             removeThis();
        }
    }
    
    private void removeThis(){
            space.removeCollisionListener(this);
            space.remove(this);
            spatial.removeFromParent();
    }
}
