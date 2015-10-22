package simulation.Controls;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.Agent;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Agent.Behavior.AgentProtectorMoveGuard;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataAgent;
import BESAFile.Data.Vector3D;
import simulation.Agent.Character;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import simulation.SmaaatApp;
import simulation.utils.Const;
import simulation.utils.Utils;

public class PositionController extends AbstractControl implements ActionListener {

    private boolean forward, backward, left, right, turnLeft, turnRight;
    private Vector3f walkDirection;
    private Vector3f viewDirection;
    private Vector3f believedPosition ;
    private Node node;
    private float delta;
    private boolean validationPosition;
    protected float speed;
    private int type;
    private String alias;
    protected double radius;
    protected double height;
    protected Position poistion;
    protected Motion motion;
    

  
    public PositionController(Node node,String alias) {
        this.node=node;
//        this.data=actionData;
    }
   
    
    public PositionController(Node node,Vector3f viewDirectio,Vector3f believedPosition ,String alias,int type, float speed,double radius,double height,Position position) {
        this.alias=alias;
        this.type=type;
        this.node=node;
        this.speed=speed;
        this.radius=radius;
        this.height=height;
        super.setSpatial(node);
        this.viewDirection=viewDirectio;
        this.walkDirection=viewDirectio;
        this.believedPosition=believedPosition;
        this.validationPosition=false;
        this.delta=0.035f;
        this.poistion=position;
        this.motion=new Motion(this.poistion.getXpos(), this.poistion.getYpos(), this.poistion.getIdfloor());
    }

    @Override
    public void setSpatial(Spatial spatial) {
    
    
    }

    private boolean differenceDelta(float a,float b,float delta){
        return Math.abs(a-b)<=delta;
    }
    
    private boolean validationPosition(){
        Vector3f pos=node.getLocalTranslation();    
        return differenceDelta(pos.x, this.believedPosition.x, delta)&&differenceDelta(pos.y, this.believedPosition.y, delta);
        //&&differenceDelta(pos.z, this.believedPosition.z, delta);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (spatial.getParent() != null) {
            if (validationPosition()){
                if (!validationPosition){
                    validationPosition=true;
                    super.enabled=false;
                    BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
                    Vector3f modelForwardDir = new Vector3f(0, 0, 0);
                    float angle = 0;
                    if(this.poistion.getYpos()==0 && this.poistion.getXpos() > 0)
                        angle = FastMath.HALF_PI;
                    if(this.poistion.getYpos()==0 && this.poistion.getXpos() < 0)
                        angle = -FastMath.HALF_PI;
                    if(this.poistion.getYpos()<0 && this.poistion.getXpos() == 0)
                        angle = FastMath.PI; 

                    modelForwardDir.y=0;

                    Vector3f walkDirection = new Vector3f(0, 0, 0);
                    walkDirection.addLocal(modelForwardDir.normalize().mult(0));
                    control.setWalkDirection(walkDirection);

                    Quaternion rotateL = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                    Vector3f viewDirection = modelForwardDir.normalize();//control.getViewDirection();
                    rotateL.multLocal(viewDirection);
                    control.setViewDirection(viewDirection);
                    ActionDataAgent ad =new ActionDataAgent("ACK",this.alias,new Position(this.motion.getXpos(), this.motion.getYpos(), this.motion.getIdfloor()));
                    Agent.sendMessage(Const.getGuardMove(this.type),this.alias, ad);
                    
                    
                }
            }else{
                validationPosition=false;
                BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
                
                Vector3f positionNode = this.spatial.getLocalTranslation();
                Vector3f modelForwardDir =new Vector3f(this.believedPosition.x-positionNode.x, this.believedPosition.y-positionNode.y, this.believedPosition.z-positionNode.z) ;
                 float angle = 0;
                 if(this.poistion.getYpos()==0 && this.poistion.getXpos() > 0)
                    angle = FastMath.HALF_PI;
                if(this.poistion.getYpos()==0 && this.poistion.getXpos() < 0)
                    angle = -FastMath.HALF_PI;
                if(this.poistion.getYpos()<0 && this.poistion.getXpos() == 0)
                    angle = FastMath.PI; 
                   modelForwardDir.y=0;
                
                Vector3f walkDirection = new Vector3f(0, 0, 0);
                walkDirection.addLocal(modelForwardDir.normalizeLocal().mult(speed));
                control.setWalkDirection(walkDirection);

                Quaternion rotateL = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                Vector3f viewDirection = modelForwardDir.normalizeLocal();//control.getViewDirection();
                rotateL.multLocal(viewDirection);
                control.setViewDirection(viewDirection);
                }
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    private void setupKeys(InputManager inputManager) {
        inputManager.addMapping("TurnLeft", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        inputManager.addMapping("TurnRight", new KeyTrigger(KeyInput.KEY_NUMPAD6));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_NUMPAD8));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_NUMPAD7));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_NUMPAD9));
        inputManager.addListener(this, "TurnLeft");
        inputManager.addListener(this, "TurnRight");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        this.resetMove();
        if (name.equals("TurnLeft")) {
            turnLeft = isPressed;
        }
        if (name.equals("TurnRight")) {
            turnRight = isPressed;
        }
        if (name.equals("Up")) {
            forward = isPressed;
        }
        if (name.equals("Down")) {
            backward = isPressed;
        }
        if (name.equals("Left")) {
            left = isPressed;
        }
        if (name.equals("Right")) {
            right = isPressed;
        }
        //keyBoardPressed = (left || right || forward || backward || turnLeft || turnRight);
    }

    
    
    private void resetMove() {
        this.forward = this.backward = this.left = this.right = this.turnLeft = this.turnRight = false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    
    public void setViewDirection(Vector3f dir) {
        this.viewDirection = dir;
        BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
        control.setViewDirection(viewDirection);
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }

    public Vector3f getBelievedPosition() {
        return believedPosition;
    }

    public void setBelievedPosition(Vector3f believedPosition) {
        this.believedPosition = believedPosition;
        this.validationPosition=false;
       
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isBackward() {
        return backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isTurnLeft() {
        return turnLeft;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }

    public boolean isTurnRight() {
        return turnRight;
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Position getPoistion() {
        return poistion;
    }

    public void setPoistion(Position poistion) {
        this.poistion = poistion;
    }

    

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public boolean isValidationPosition() {
        return validationPosition;
    }

    public void setValidationPosition(boolean validationPosition) {
        this.validationPosition = validationPosition;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Motion getMotion() {
        return motion;
    }

    public void setMotion(Motion motion) {
        this.motion = motion;
    }
    
    

    
    
    
    

    
}
