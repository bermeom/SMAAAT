package simulation.Controls;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.DataBESA;
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
import BESAFile.World.Behavior.UpdateGuardJME;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private boolean move;
    protected float speed;
    private int type;
    private String alias;
    protected double radius;
    protected double height;
    protected Position poistion;
    protected Motion motion;
    protected Vector3f modelForwardDir ;
    protected int contOut; 
    protected int limitContOut; 
    // message
    protected int reply_with;
    protected int in_reply_to;
    protected ActionData data;

  
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
        this.modelForwardDir=new Vector3f(1,0,0);
        this.motion=new Motion(this.poistion.getXpos(), this.poistion.getYpos(), this.poistion.getIdfloor());
        this.move=false;
        moveCharacter(modelForwardDir,0 );
        this.limitContOut=1000;
    }

    @Override
    public void setSpatial(Spatial spatial) {
    
    
    }

    private boolean differenceDelta(float a,float b,float delta){
        return Math.abs(a-b)<=delta;
    }
    
    private double euclidesDistance(Vector3f a,Vector3f b){
       return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getZ()-b.getZ())*(a.getZ()-b.getZ()));
    
    }
    private boolean validationPosition(){
        Vector3f pos=node.getLocalTranslation(); 
        return euclidesDistance(pos, this.believedPosition)<=delta;
    }
    
    
    private boolean moveCharacter(Vector3f modelForwardDir,float speed_){
        BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
        try {
            control.setWalkDirection(modelForwardDir.normalizeLocal().mult(speed_));
            control.setViewDirection(modelForwardDir.normalize());                    
        } catch (Exception e) {
            System.out.println(" -------------- ERROR  -------------   ");
            Logger.getLogger(this.alias).log(Level.SEVERE, null, e);
        }
        return true;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (spatial.getParent() != null) {
            //System.out.println(contOut+" ---> "+alias+" :  "+validationPosition+" "+this.spatial.getLocalTranslation()+" "+ validationPosition()+" "+this.believedPosition);
            if (validationPosition()){
                if (!validationPosition){
                    this.contOut=0;
                    if (this.data!=null){
                        this.data.setAction("moveACK");
                        sendMessage(UpdateGuardJME.class,Const.World, this.data);
                    }
                    validationPosition=true;
                    moveCharacter(modelForwardDir,0 );
                    
                }else if(contOut>=this.limitContOut){
                    //System.out.println("++++++++++++++>>>>>>> CONTOUT "+this.alias+"<<<<<<<<<<<<<<<<<<<<<<");
                    if (this.data!=null){
                        this.data.setAction("moveACK");
                        sendMessage(UpdateGuardJME.class,Const.World, this.data);
                    }
                }
                this.contOut++;
                this.move=false;
            }else{
               
                if (!this.move){
                    this.move=true;
                    this.contOut=0;
                }else{
                    contOut++;
                    if (contOut>=this.limitContOut){
                        //System.out.println("++++++++++++++>>>>>>> CONTOUT "+this.alias+"<<<<<<<<<<<<<<<<<<<<<<");
                        sendMessage(UpdateGuardJME.class,Const.World, this.data);
                        if (this.data!=null){
                            this.data.setAction("moveNACK");
                            sendMessage(UpdateGuardJME.class,Const.World, this.data);
                        }
                    }
                }
                validationPosition=false;
                Vector3f positionNode = this.spatial.getLocalTranslation();
                modelForwardDir =new Vector3f(this.believedPosition.x-positionNode.x, 0, this.believedPosition.z-positionNode.z) ;
                modelForwardDir.y=0;
                moveCharacter(modelForwardDir, speed);
               }
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    public void sendMessage(Class guard, String alias, DataBESA data) {
        
        EventBESA ev = new EventBESA(guard.getName(), data);
        try {
            AgHandlerBESA ah = AdmBESA.getInstance().getHandlerByAlias(alias);
            ah.sendEvent(ev);
        } catch (ExceptionBESA ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("+++ Send ACK "+alias);
    }
    

    public void onAction(String name, boolean isPressed, float tpf) {
   
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

    public int getReply_with() {
        return reply_with;
    }

    public void setReply_with(int reply_with) {
        this.reply_with = reply_with;
    }

    public int getIn_reply_to() {
        return in_reply_to;
    }

    public void setIn_reply_to(int in_reply_to) {
        this.contOut=0;
        this.in_reply_to = in_reply_to;
    }

    public ActionData getData() {
        return data;
    }

    public void setData(ActionData data) {
        this.data = data;
    }
    
    
    
    
    

    
    
    
    

}
