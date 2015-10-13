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
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.World.State.WorldState;
import BESAFile.World.State.WorldStateJME;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author berme_000
 */
public class UpdateGuardJME extends GuardBESA {

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
        Spatial s = state.getApp().getRootNode().getChild(data.getAlias());
        BetterCharacterControl control = s.getControl(BetterCharacterControl.class);

        float angle = 0;
        
        if(data.getYpos()==0 && data.getXpos() > 0)
            angle = FastMath.HALF_PI;
        if(data.getYpos()==0 && data.getXpos() < 0)
            angle = -FastMath.HALF_PI;
        if(data.getYpos()<0 && data.getXpos() == 0)
            angle = FastMath.PI;
        
        Vector3f modelForwardDir = s.getWorldRotation().mult(Vector3f.UNIT_Z.negate());
        Vector3f walkDirection = new Vector3f();
        walkDirection.set(0, 0, 0);
        walkDirection.addLocal(modelForwardDir);
        control.setWalkDirection(walkDirection);
        

        Quaternion rotateL = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        Vector3f viewDirection = control.getViewDirection();
        rotateL.multLocal(viewDirection);
        control.setViewDirection(viewDirection);

        
    }
}
