package simulation;

import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelFloor;
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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class SmaaatApp extends SimpleApplication implements ActionListener {

    private DirectionalLight cameraLight;
    private BulletAppState bulletAppState;
    private Node ediffice;
    private ModelEdifice mEdifice;
    public static void main(String[] args) {
        SmaaatApp app = new SmaaatApp();
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        createEdifice();
        viewPort.setBackgroundColor(new ColorRGBA(0f, 0f, 0f, 1f));
        flyCam.setMoveSpeed(20);
        
        
        //Spatial floorSpatial = assetManager.loadModel("Models/floor.j3o");
        //rootNode.attachChild(machineSpatial);
        
        //cam.setLocation(floorSpatial.getWorldTranslation().add(0, 10, 10));
        //cam.lookAt(floorSpatial.getWorldTranslation(), Vector3f.UNIT_Y);
        cam.setLocation(new Vector3f(0, 10, 10));
        cam.lookAt(new Vector3f(0, -10, -5), Vector3f.UNIT_Y);
        //*/
        /*
        cam.setLocation(floor.getWorldTranslation());
        cam.lookAt(floor.getWorldTranslation(), Vector3f.UNIT_Y);
        //*/
        setupLighting();
        setupKeys();

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //bulletAppState.setDebugEnabled(true);

        /*
        CollisionShape floorShape = CollisionShapeFactory.createMeshShape((Node) floorSpatial);
        RigidBodyControl floorRigidBody = new RigidBodyControl(floorShape, 0);
        floorSpatial.addControl(floorRigidBody);
        bulletAppState.getPhysicsSpace().add(floorRigidBody);
        //*/
        CollisionShape floorShape = CollisionShapeFactory.createMeshShape((Node) ediffice);
        RigidBodyControl floorRigidBody = new RigidBodyControl(floorShape, 0);
        ediffice.addControl(floorRigidBody);
        bulletAppState.getPhysicsSpace().add(floorRigidBody);
        
        //*/
        //loorSpatial;
        createAgent();

    }
    
    private void createEdifice(){
        int width=10;
        int length=10;
        int x=0;
        int y=0;
        int z=0;
        int nFloors=1;
        mEdifice=new ModelEdifice(width, length, nFloors);
        mEdifice.setPostGridFloor(0,4 ,0, 'H');
        mEdifice.setPostGridFloor(0,4 ,1, 'H');
        mEdifice.setPostGridFloor(0,4 ,2, 'H');
        mEdifice.setPostGridFloor(0,6 ,9, 'H');
        mEdifice.setPostGridFloor(0,6 ,8, 'H');
        mEdifice.setPostGridFloor(0,6 ,7, 'H');
        mEdifice.setPostGridFloor(0,6 ,6, 'H');
        mEdifice.setPostGridFloor(0,0 ,6, 'V');
        mEdifice.setPostGridFloor(0,1 ,6, 'V');
        mEdifice.setPostGridFloor(0,2 ,6, 'V');
        mEdifice.setPostGridFloor(0,3 ,6, 'V');
        mEdifice.setPostGridFloor(0,6 ,2, 'b');
        mEdifice.setPostGridFloor(0,9 ,0, 'B');
        mEdifice.setPostGridFloor(0,9 ,9, 'E');
        System.out.println(mEdifice);
        createVirtualEdifice(width, length, x, y, z);
    }
    
    
    private void createVirtualEdifice(int width, int length,int x,int y,int z){
        ediffice =new Node("Edifice");//*/
        for (int n=0;n<mEdifice.getnFlooors();n++){
            ModelFloor mf= mEdifice.getFloor(n);
            WorldFloor we=new WorldFloor(assetManager, width, length, x, y-5*n, z);
            Node floor=new Node("Floor"+n);
            ediffice.attachChild(floor);
            floor.attachChild(we.makeFloor());
            floor.attachChild(we.makeGridFloor(ColorRGBA.Blue));
            floor.attachChild(we.makeWallFloor1());
            floor.attachChild(we.makeWallFloor2());
            floor.attachChild(we.makeWallFloor3());
            floor.attachChild(we.makeWallFloor4());
            for (int i=0;i<width;i++){
                for (int j=0;j<length;j++){
                     switch(mf.get(i, j)){
                         case 'H':floor.attachChild(we.makeWall("wallH"+n+""+i+""+j, i, j,false)); break;
                         case 'V':floor.attachChild(we.makeWall("wallV"+n+""+i+""+j, i, j,true)); break;
                         case 'b':floor.attachChild(we.makeCubeb("cubeb"+n+""+i+""+j, i, j,0.3f)); break;
                         case 'B':floor.attachChild(we.makeCubeB("cubeB"+n+""+i+""+j, i, j)); break;
                     }
                }
            }
        }
        rootNode.attachChild(ediffice);
        
    }
    
    private void createAgent(){
        //bulletAppState.setDebugEnabled(true);
        float radius = 1f;
        float height = radius*2;
        Node agentNode = new Node("Agent");
        ((Spatial)(agentNode)).setUserData("radius", radius);
        ((Spatial)(agentNode)).setUserData("height", height);
        Box box = new Box(1, 1, 1);
        Geometry cube = new Geometry("AgentCube", box);
        //cube.setLocalTranslation(x+width-post(i), y+1.2f, z+length-post(j));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
              assetManager.loadTexture("Textures/texture3.jpg"));
        mat1.setColor("Color", ColorRGBA.Blue);
        cube.setMaterial(mat1);
        agentNode.attachChild(createModelExplorer());
        agentNode.setLocalTranslation(new Vector3f(4, 1, 2));
        BetterCharacterControl physicsCharacter = new BetterCharacterControl(radius, height, 1.0f);
        agentNode.addControl(physicsCharacter);
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
        WalkerNavControl wNavControl = new WalkerNavControl(inputManager, assetManager, ediffice);
        agentNode.addControl(wNavControl);

        rootNode.attachChild(agentNode);
    
    }
    
    private Spatial createModelProtector(){
        Spatial machineSpatial = assetManager.loadModel("Models/AgentProtector/ED-209.j3o");
        machineSpatial.scale(.3f);
        //machineSpatial.rotate(0, FastMath.PI, 0);
        //machineSpatial.setLocalTranslation(1.5f, 1.5f, 0);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    private Spatial createModelExplorer(){
        Spatial machineSpatial = assetManager.loadModel("Models/AgentExplorer/Drone/Drone.j3o");
        machineSpatial.scale(.3f);
        //machineSpatial.rotate(0, 2*FastMath.PI, 0);
        machineSpatial.setLocalTranslation(0f, 1.5f, 0);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    private Spatial createModelEnemy(){
        Spatial machineSpatial = assetManager.loadModel("Models/AgentEnemy/Marvin_Firefighter/Marvin_Firefighter.j3o");
        machineSpatial.scale(.5f);
        //machineSpatial.scale(.15f);
        //machineSpatial.rotate(0, -FastMath.PI/2, 0);
        //machineSpatial.setLocalTranslation(0, 1.2f, 0);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    public void setupLighting() {
        cameraLight = new DirectionalLight();
        cameraLight.setColor(ColorRGBA.White);
        cameraLight.setDirection(cam.getDirection().normalizeLocal());
        rootNode.addLight(cameraLight);
        /*
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0f, -1f, 0f));
        //sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
        //*/
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
