package simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SmaaatApp extends SimpleApplication implements ActionListener {

    private DirectionalLight cameraLight;
    private BulletAppState bulletAppState;
    private Node floor;
    public static void main(String[] args) {
        SmaaatApp app = new SmaaatApp();
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {

        viewPort.setBackgroundColor(new ColorRGBA(0f, 0f, 0f, 1f));
        flyCam.setMoveSpeed(20);
        
        createFloorN(7,7,0,-8,0);
        
        Spatial floorSpatial = assetManager.loadModel("Models/floor.j3o");
        rootNode.attachChild(floorSpatial);
        
        cam.setLocation(floorSpatial.getWorldTranslation().add(0, 10, 10));
        cam.lookAt(floorSpatial.getWorldTranslation(), Vector3f.UNIT_Y);
        //*/
        /*
        cam.setLocation(floor.getWorldTranslation());
        cam.lookAt(floor.getWorldTranslation(), Vector3f.UNIT_Y);
        //*/
        setupLighting();
        setupKeys();

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);

        /*
        CollisionShape floorShape = CollisionShapeFactory.createMeshShape((Node) floorSpatial);
        RigidBodyControl floorRigidBody = new RigidBodyControl(floorShape, 0);
        floorSpatial.addControl(floorRigidBody);
        bulletAppState.getPhysicsSpace().add(floorRigidBody);
        //*/
        CollisionShape floorShape = CollisionShapeFactory.createMeshShape((Node) floorSpatial);
        RigidBodyControl floorRigidBody = new RigidBodyControl(floorShape, 0);
        floorSpatial.addControl(floorRigidBody);
        bulletAppState.getPhysicsSpace().add(floorRigidBody);
        
        //*/
        
        float radius = 0.2f;
        float height = 0.4f;
        Node characterNode = new Node("CharacterNode");
        ((Spatial)(characterNode)).setUserData("radius", radius);
        ((Spatial)(characterNode)).setUserData("height", height);
        characterNode.setLocalTranslation(new Vector3f(4, 0, 2));
        BetterCharacterControl physicsCharacter = new BetterCharacterControl(radius, height, 1.0f);
        characterNode.addControl(physicsCharacter);
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
        WalkerNavControl wNavControl = new WalkerNavControl(inputManager, assetManager, floorSpatial);
        characterNode.addControl(wNavControl);

        rootNode.attachChild(characterNode);

    }
    
    private void createFloorN(int width, int length,int x,int y,int z){
        WorldFloor we=new WorldFloor(assetManager, width, length, x, y, z);
        floor =new Node("Shootables");//*/
        rootNode.attachChild(floor);
        floor.attachChild(we.makeFloor());
        floor.attachChild(we.makeGridFloor(ColorRGBA.Blue));
        floor.attachChild(we.makeWallFloor1());
        floor.attachChild(we.makeWallFloor2());
        floor.attachChild(we.makeWallFloor3());
        floor.attachChild(we.makeWallFloor4());
        floor.attachChild(we.makeWall("wall", 0, 3,true));
        floor.attachChild(we.makeWall("wall1",1, 3,true));
        floor.attachChild(we.makeCube("cube",2, 3));
        floor.attachChild(we.makeWall("wall1",2, 2,false));
        floor.attachChild(we.makeWall("wall1",2, 4,false));
    }
    
    public void setupLighting() {
        cameraLight = new DirectionalLight();
        cameraLight.setColor(ColorRGBA.White);
        cameraLight.setDirection(cam.getDirection().normalizeLocal());
        rootNode.addLight(cameraLight);
    }

    @Override
    public void simpleUpdate(float tpf) {
        cameraLight.setDirection(cam.getDirection().normalizeLocal());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void setupKeys() {
        inputManager.addMapping("KEY_1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addListener(this, "KEY_1");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("KEY_1")){
            Vector3f playerPos = rootNode.getChild("CharacterNode").getWorldTranslation();
            //cam.setLocation(new Vector3f(0,3,0));//UP
            cam.setLocation(playerPos.add(new Vector3f(0,3,0)));//SIDE
            cam.lookAt(playerPos, Vector3f.UNIT_Y);
        }
    }
}
