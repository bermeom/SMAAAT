package simulation;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import simulation.utils.Utils;

public class WalkerNavControl extends AbstractControl implements ActionListener {

    private boolean forward, backward, left, right, turnLeft, turnRight, slow = false;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 1);
    private AssetManager assetManager;
    private Spatial arrow;
    private Spatial floor;

    public WalkerNavControl(InputManager inputManager, AssetManager assetManager, Spatial floor) {
        this.assetManager = assetManager;
        setupKeys(inputManager);
        arrow = Utils.createDebugArrow(assetManager, Vector3f.ZERO, Vector3f.UNIT_Z, null);
        this.floor = floor;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        ((Node) spatial).attachChild(arrow);
        float height = spatial.getUserData("height");
        arrow.getLocalTranslation().setY(height / 2);
    }

    @Override
    protected void controlUpdate(float tpf) {
        float speed = 3;
        if (slow) {
            speed *= 0.2;
        }
        if (!keyBoardPressed) {
            wallFollowerMovement(tpf);
        }

        BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        walkDirection.set(0, 0, 0);
        if (left || right) {
            modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
            if (right) {
                modelForwardDir.negateLocal();
            }
            walkDirection.addLocal(modelForwardDir.mult(speed));
        }
        if (forward) {
            walkDirection.addLocal(modelForwardDir.mult(speed));
        }
        if (backward) {
            walkDirection.addLocal(modelForwardDir.negate().multLocal(speed));
        }
        control.setWalkDirection(walkDirection);

        if (turnLeft) {
            Quaternion rotateL = new Quaternion().fromAngleAxis(FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateL.multLocal(viewDirection);
        }
        if (turnRight) {
            Quaternion rotateR = new Quaternion().fromAngleAxis(-FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateR.multLocal(viewDirection);
        }
        control.setViewDirection(viewDirection);
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
        keyBoardPressed = (left || right || forward || backward || turnLeft || turnRight);
    }
    SensorRay sr1 = new SensorRay();
    SensorRay sr2 = new SensorRay();
    SensorRay sr3 = new SensorRay();
    SensorRay sr4 = new SensorRay();
    SensorRay sr5 = new SensorRay();
    SensorRay sr6 = new SensorRay();
    SensorRay sr7 = new SensorRay();
    SensorRay sr8 = new SensorRay();
    boolean allowMove = true;
    boolean keyBoardPressed = false;

    private void wallFollowerMovement(float tpf) {

        float radius = spatial.getUserData("radius");
        float height = spatial.getUserData("height");
        float sensorLimit = DistanceSensorData.max;
        radius -= 0.03f;

        BetterCharacterControl control = spatial.getControl(BetterCharacterControl.class);
        Vector3f dir = control.getViewDirection().normalize();
        Vector3f pos = spatial.getWorldTranslation().clone();
        pos.setY(height / 2);
        Quaternion q = new Quaternion();
        q.lookAt(dir, Vector3f.UNIT_Y);

        Vector3f dirPerpendicular = new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y).mult(dir);

        Ray rFrontL = new Ray(pos.add(q.mult(new Vector3f(radius, 0, radius))), dir);
        Ray rFrontR = new Ray(pos.add(q.mult(new Vector3f(-radius, 0, radius))), dir);

        Ray rRightF = new Ray(pos.add(q.mult(new Vector3f(-radius, 0, /**/-radius))), dirPerpendicular.negate());
        Ray rRightR = new Ray(pos.add(q.mult(new Vector3f(-radius, 0, -radius-(radius*0.5f)))), dirPerpendicular.negate());

        Ray rRearR = new Ray(pos.add(q.mult(new Vector3f(-radius, 0, -radius))), dir.negate());
        Ray rRearL = new Ray(pos.add(q.mult(new Vector3f(radius, 0, -radius))), dir.negate());

        Ray rLeftR = new Ray(pos.add(q.mult(new Vector3f(radius, 0, -radius-(radius*0.5f)))), dirPerpendicular);
        Ray rLeftF = new Ray(pos.add(q.mult(new Vector3f(radius, 0, /**/-radius))), dirPerpendicular);

        rFrontL.setLimit(sensorLimit);
        rFrontR.setLimit(sensorLimit);
        rRightF.setLimit(sensorLimit);
        rRightR.setLimit(sensorLimit);
        rRearR.setLimit(sensorLimit);
        rRearL.setLimit(sensorLimit);
        rLeftR.setLimit(sensorLimit);
        rLeftF.setLimit(sensorLimit);

        sr1.ray = rFrontL;
        sr2.ray = rFrontR;
        sr3.ray = rRightF;
        sr4.ray = rRightR;
        sr5.ray = rRearR;
        sr6.ray = rRearL;
        sr7.ray = rLeftR;
        sr8.ray = rLeftF;

        DistanceSensorData sdFrontL = checkWallCollision(sr1);
        DistanceSensorData sdFrontR = checkWallCollision(sr2);
        DistanceSensorData sdRightF = checkWallCollision(sr3);
        DistanceSensorData sdRightR = checkWallCollision(sr4);
        DistanceSensorData sdRearR = checkWallCollision(sr5);
        DistanceSensorData sdRearL = checkWallCollision(sr6);
        DistanceSensorData sdLeftR = checkWallCollision(sr7);
        DistanceSensorData sdLeftF = checkWallCollision(sr8);

        debugRayGeometry(sr1);
        debugRayGeometry(sr2);
        debugRayGeometry(sr3);
        debugRayGeometry(sr4);
        debugRayGeometry(sr5);
        debugRayGeometry(sr6);
        debugRayGeometry(sr7);
        debugRayGeometry(sr8);


        if (allowMove) {
            this.resetMove();
            /*No hay obstaculo en ningun sensor: avanza adelante*/
            if (!sdFrontL.collideWithObject && !sdFrontR.collideWithObject
                    && !sdRightF.collideWithObject && !sdRightR.collideWithObject
                    && !sdRearL.collideWithObject && !sdRearR.collideWithObject
                    && !sdLeftF.collideWithObject && !sdLeftR.collideWithObject) {
                this.forward = true;
            }
            /*Los dos sensores delanteros detectan objeto: Girar 90º Izquierda*/
            if (sdFrontL.collideWithObject && sdFrontR.collideWithObject) {
                Quaternion rotateL = new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
                rotateL.multLocal(viewDirection);
                control.setViewDirection(viewDirection);
            }
            /*Los dos sensores de la derecha detectan objeto: avanza adelante*/
            if (sdRightF.collideWithObject && sdRightR.collideWithObject ||
                  sdRightF.collideWithObject && !sdRightR.collideWithObject ) {
                this.forward = true;
            }
            /*Sensor derecho frontal no detecta pero sensor derecho trasero si detecta: Gira a la derecha 90º*/
            if (!sdRightF.collideWithObject && sdRightR.collideWithObject) {
                Quaternion rotateL = new Quaternion().fromAngleAxis(-FastMath.DEG_TO_RAD * 90.0f, Vector3f.UNIT_Y);
                rotateL.multLocal(viewDirection);
                control.setViewDirection(viewDirection);
                //this.forward = true;
                this.slow = true;
            }

        }
        /*
        System.out.println("FL: " + sdFrontL.collideWithObject + " FR: " + sdFrontR.collideWithObject
                + " RF: " + sdRightF.collideWithObject + " RR: " + sdRightR.collideWithObject
                + " RL: " + sdRearL.collideWithObject + " RR: " + sdRearR.collideWithObject
                + " LF: " + sdLeftF.collideWithObject + " LR: " + sdLeftR.collideWithObject);
             */

    }
    
    private void resetMove()
    {
        this.forward = this.backward = this.left = this.right = this.slow = this.turnLeft = this.turnRight = false;
    }
    
    private void debugRayGeometry(SensorRay sr) {
        Vector3f pos = sr.ray.origin;
        Vector3f dir = sr.ray.direction.normalize();
        if (sr.vectorGeometry == null) {
            sr.vectorGeometry = Utils.createDebugArrow(assetManager, pos, dir.mult(sr.ray.limit), ((Node) spatial).getParent());
        } else {
            sr.vectorGeometry.setLocalTranslation(pos);
            Quaternion q = new Quaternion();
            q.lookAt(dir, Vector3f.UNIT_Y);
            sr.vectorGeometry.setLocalRotation(q);
        }

        if (sr.contactPointGeometry == null) {
            if (sr.contactPoint != null) {
                sr.contactPointGeometry = Utils.createDebugBox(assetManager, sr.contactPoint, 0.02f, ((Node) spatial).getParent());
            }
        } else {
            if (sr.contactPoint != null) {
                ((Node) spatial).getParent().attachChild(sr.contactPointGeometry);
                sr.contactPointGeometry.setLocalTranslation(sr.contactPoint);
            } else {
                sr.contactPointGeometry.removeFromParent();
            }
        }
    }

    private DistanceSensorData checkWallCollision(SensorRay sr) {
        sr.contactPoint = null;
        Ray ray = sr.ray;
        DistanceSensorData sd = new DistanceSensorData();
        CollisionResults cr = new CollisionResults();
        floor.collideWith(ray, cr);
        if (cr.size() > 0) {
            CollisionResult c = cr.getClosestCollision();
            float distance = ray.origin.distance(c.getContactPoint());
            if (distance < DistanceSensorData.max) {
                sr.contactPoint = c.getContactPoint();
                sd.collideWithObject = true;
                sd.collisionDistance = distance;
            }
        }
        return sd;
    }

    protected class SensorRay {

        public Ray ray;
        public Spatial contactPointGeometry;
        public Vector3f contactPoint;
        public Spatial vectorGeometry;
    }

    protected class DistanceSensorData {

        public final static float min = 0.05f;
        public final static float max = 0.15f;
        public float collisionDistance = 0;
        public boolean collideWithObject = false;
    }
}
