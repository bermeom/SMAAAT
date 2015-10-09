package simulation;

import BESA.ExceptionBESA;
import BESA.Kernell.Agent.Event.EventBESA;
import BESA.Kernell.Agent.StructBESA;
import BESA.Kernell.System.AdmBESA;
import BESA.Kernell.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import BESAFile.Agent.AgentProtector;
import BESAFile.Agent.Behavior.AgentMoveGuard;
import BESAFile.Agent.Behavior.AgentProtectorMoveGuard;
import BESAFile.Agent.State.AgentProtectorState;
import BESAFile.Agent.State.AgentState;
import BESAFile.Data.ActionData;
import BESAFile.Data.ActionDataWalkerNav;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelFloor;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
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
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Agent.EnemyAgent;
import simulation.Agent.ExplorerAgent;
import simulation.Agent.GuardianAgent;
import simulation.Agent.HostageAgent;
import simulation.world.Exit;

public class SmaaatApp extends SimpleApplication implements ActionListener {

    private DirectionalLight cameraLight;
    private BulletAppState bulletAppState;
    private Node ediffice;
    private ModelEdifice mEdifice;
    private Node characterNode;
    private float distBetweenFloors;
    private float x;
    private float y;
    private float z;
    private int width;
    private int length;
    private int nFloors;
    private int consecutiveAgenProtector;// 
    private int consecutiveAgenEnemy;
    private int consecutiveAgenHostage;
    private int consecutiveAgenExplorer;
    private static AdmBESA admLocal;
    private double passwdAg;
    
    public static void main(String[] args) {
        SmaaatApp app = new SmaaatApp();
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        AppSettings settings = new AppSettings(true);
        settings.setTitle("SMAAAT");
        app.setSettings(settings);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        distBetweenFloors=5;
        consecutiveAgenProtector=0;
        consecutiveAgenEnemy=0;
        consecutiveAgenExplorer=0;
        consecutiveAgenHostage=0;
        
        createEdifice();
        setupBesa();
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

        CollisionShape floorShape = CollisionShapeFactory.createMeshShape((Node) ediffice);
        RigidBodyControl floorRigidBody = new RigidBodyControl(floorShape, 0);
        ediffice.addControl(floorRigidBody);
        bulletAppState.getPhysicsSpace().add(floorRigidBody);
        //*/
        //createAgent();
        
        characterNode = new Node();
        try {
            //new EnemyAgent(this, getPositionVirtiul(0, 5, 5), new Vector3f(0, 0, -1),""+1,0.75f,createModelEnemy());
            //new HostageAgent(this, getPositionVirtiul(0, 3, 4), new Vector3f(1, 0, 0),""+1,0.5f,createModelHostage());
            //new ExplorerAgent(this, getPositionVirtiul(0, 4, 4), new Vector3f(-1, 0, 0),""+1,0.75f,createModelExplorer());
            createAgentProtector(0, 1, 1, new Vector3f(0, 0, -1));
            createAgentProtector(0, 9, 9, new Vector3f(0, 0, -1));
            
            createAgentEnemy(0, 5, 6, new Vector3f(0, 0, -1));
            createAgentEnemy(0, 0, 9, new Vector3f(0, 0, 1));
            createAgentHostage(0, 3, 4, new Vector3f(1, 0, 0));
            createAgentExplorer(0, 6, 4, new Vector3f(-1, 0, 0));
            //*/
        } catch (ExceptionBESA ex) {
            Logger.getLogger(SmaaatApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        new GuardianAgent(this, new Vector3f(-4.5f, 1, 2), new Vector3f(0, 0, -1));
        new EnemyAgent(this, new Vector3f(3, 1, 1), new Vector3f(0, 0, 1));
        new EnemyAgent(this, new Vector3f(2, 1, -2), new Vector3f(0, 0, 1));
        new EnemyAgent(this, new Vector3f(-4, 1, -2), new Vector3f(0, 0, 1));
        new HostageAgent(this, new Vector3f(-1, 1, 2), new Vector3f(1, 0, 0));
        new HostageAgent(this, new Vector3f(-1, 1, -0.5f), new Vector3f(1, 0, 0));
        new ExplorerAgent(this, new Vector3f(-3, 1, -1), new Vector3f(-1, 0, 0));
        */
        //Exit e = new Exit(this, getPositionVirtiul(0, 7, 0), new Vector3f(0.5f,1,0.1f));
        
        rootNode.attachChild(characterNode);
        sendEventAgentMove();
    }
    
    private void sendEventAgentMove() {
        
        ActionDataWalkerNav actionData=new ActionDataWalkerNav(1,"move");
        EventBESA event = new EventBESA(AgentProtectorMoveGuard.class.getName(), actionData);
        AgHandlerBESA ah;
        boolean sw=true;
        do{
            try {
                ah =SmaaatApp.admLocal.getHandlerByAlias("GuardianAgent0");
                ah.sendEvent(event);
                sw=false;
            } catch (ExceptionBESA e) {
                ReportBESA.error(e);
                sw=true;
            }
        }while(sw);
        System.out.println("   -------------------------- Move Agent --------------- ");
    }
    
    private void createAgentProtector(int idFloor,int i,int j,Vector3f direction) throws ExceptionBESA{
    
        GuardianAgent agente=new GuardianAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenProtector,0.75f,createModelProtector(),admLocal);
        consecutiveAgenProtector++;
        
        //AgentProtectorState state = new AgentProtectorState(ga,new ModelEdifice(width, length, nFloors),i,j,idFloor,ga.getAlias());
        AgentProtectorState state = new AgentProtectorState(agente,mEdifice,i,j,idFloor,agente.getAlias());
        StructBESA struct = new StructBESA();
        struct.addBehavior("agentMove");
        struct.bindGuard("agentMove", AgentProtectorMoveGuard.class);
        AgentProtector agent = new AgentProtector(state.getAlias(), state, struct, passwdAg);
        agent.start();
        //*/
    }
    
     private void createAgentEnemy(int idFloor,int i,int j,Vector3f direction) throws ExceptionBESA{
    
        //EnemyAgent agente=new EnemyAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenEnemy,0.75f,createModelEnemy(),admLocal);
        EnemyAgent agente=new EnemyAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenEnemy,0.75f,createModelEnemy());
        /*AgentProtectorState state = new AgentProtectorState(agente,mEdifice,i,j,idFloor,agente.getAlias());
        StructBESA struct = new StructBESA();
        struct.addBehavior("agentMove");
        struct.bindGuard("agentMove", AgentProtectorMoveGuard.class);
        AgentProtector agent = new AgentProtector(state.getAlias(), state, struct, passwdAg);
        agent.start();*/
        consecutiveAgenEnemy++;
     }
     
     private void createAgentHostage(int idFloor,int i,int j,Vector3f direction) throws ExceptionBESA{
    
        //HostageAgent agente=new HostageAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenHostage,0.5f,createModelHostage(),admLocal);
        HostageAgent agente=new HostageAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenHostage,0.5f,createModelHostage());
        consecutiveAgenHostage++;
     }
     
     private void createAgentExplorer(int idFloor,int i,int j,Vector3f direction) throws ExceptionBESA{
    
        //ExplorerAgent agente=new ExplorerAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenExplorer,0.75f,createModelExplorer(),admLocal);
        ExplorerAgent agente=new ExplorerAgent(this, getPositionVirtiul(idFloor, i, j), direction,""+consecutiveAgenExplorer,0.75f,createModelExplorer());
        consecutiveAgenExplorer++;
     }
    
    private Vector3f getPositionVirtiul(int idFloor,int i,int j){
        return new Vector3f(x+width-post(i), y-distBetweenFloors*idFloor+1, z+length-post(j));
    }
    
    private int post(int i){
        return i*2+1;
    }
    private void createEdifice(){
        width=10;
        length=10;
        x=0;
        y=0;
        z=0;
        nFloors=2;
        mEdifice=new ModelEdifice(width, length, nFloors);
        for (int i=0;i<nFloors;i++){
            mEdifice.setPostGridFloor(i,4 ,0, 'H');
            mEdifice.setPostGridFloor(i,4 ,1, 'H');
            mEdifice.setPostGridFloor(i,4 ,2, 'H');
            mEdifice.setPostGridFloor(i,6 ,9, 'H');
            mEdifice.setPostGridFloor(i,6 ,8, 'H');
            mEdifice.setPostGridFloor(i,6 ,7, 'H');
            mEdifice.setPostGridFloor(i,6 ,6, 'H');
            mEdifice.setPostGridFloor(i,8 ,5, 'V');
            mEdifice.setPostGridFloor(i,9 ,5, 'V');
            mEdifice.setPostGridFloor(i,0 ,6, 'V');
            mEdifice.setPostGridFloor(i,1 ,6, 'V');
            mEdifice.setPostGridFloor(i,2 ,6, 'V');
            mEdifice.setPostGridFloor(i,3 ,6, 'V');
            mEdifice.setPostGridFloor(i,6 ,0, 'b');
            mEdifice.setPostGridFloor(i,9,4, 'B');
            mEdifice.setPostGridFloor(i,4,3, 'H');
            mEdifice.setPostGridFloor(i,9 ,0, 'B');
            mEdifice.setPostGridFloor(i,9 ,9, 'E');
        }
        //System.out.println(mEdifice);
        createVirtualEdifice();
    }
    
    
    private void createVirtualEdifice(){
        ediffice =new Node("Edifice");//*/
        for (int n=0;n<mEdifice.getnFlooors();n++){
            ModelFloor mf= mEdifice.getFloor(n);
            WorldFloor we=new WorldFloor(assetManager, width, length, x, y-distBetweenFloors*n, z);
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
        machineSpatial.scale(.25f);
        //machineSpatial.rotate(0, 2*FastMath.PI, 0);
        machineSpatial.setLocalTranslation(0f, 1.5f, 0);
        //machineSpatial.setMaterial(mat1);
        return machineSpatial;
    }

    private Spatial createModelHostage(){
        Spatial machineSpatial = assetManager.loadModel("Models/AgentHostage/Android/android.j3o");
        machineSpatial.scale(0.3f);
        //machineSpatial.scale(.15f);
        //machineSpatial.rotate(0, -FastMath.PI/2, 0);
        machineSpatial.setLocalTranslation(0, 0.5f, 0);
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
    
    private void setupBesa(){
        passwdAg = 0.91;
       SmaaatApp.admLocal = AdmBESA.getInstance();
        
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
        inputManager.addMapping("KEY_2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addListener(this, "KEY_2");
        inputManager.addMapping("KEY_3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addListener(this, "KEY_3");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        
        Vector3f position = Vector3f.ZERO;
        if (name.equals("KEY_1")) {
            cam.setLocation(position.add(new Vector3f(0, 3, 0)));
            cam.lookAt(position, Vector3f.UNIT_Y);
        }
        if (name.equals("KEY_2")) {
            cam.setLocation(position.add(new Vector3f(3, 0, 0)));
            cam.lookAt(position, Vector3f.UNIT_Y);
        }
        if(name.equals("KEY_3"))
        {
            
        }
    }
    
        public BulletAppState getBulletAppState() {
        return this.bulletAppState;
    }

    public PhysicsSpace getPhysicsSpace() {
        return this.bulletAppState.getPhysicsSpace();
    }

    public Spatial getFloor() {
        return this.ediffice;
    }

    public Node getCharacterNode() {
        return this.characterNode;
    }
}
