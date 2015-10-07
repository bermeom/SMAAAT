package simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Test extends SimpleApplication implements ActionListener {

    public static void main(String[] args) {
        Test app = new Test();
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }
    Geometry boxGeometry;
    Geometry sensor;
    private boolean forward, backward, left, right = false;

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(20);
        setupKeys(inputManager);

        Box b = new Box(1, 1, 1);
        boxGeometry = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        mat.getAdditionalRenderState().setWireframe(true);
        boxGeometry.setMaterial(mat);
        boxGeometry.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(boxGeometry);
        
        //VECTORS
        
        Vector3f origen = Vector3f.UNIT_X.add(Vector3f.UNIT_Y);
        Vector3f direccion = Vector3f.UNIT_Y;
        sensor = Utils.createDebugArrow(assetManager, origen, direccion, rootNode);
        
    }

    @Override
    public void simpleUpdate(float tpf) {

        if (left || right) {
            float phi = FastMath.HALF_PI * (right ? -1 : 1);
            Quaternion q = new Quaternion().fromAngleAxis(phi * tpf, Vector3f.UNIT_Z);
            Quaternion r = boxGeometry.getLocalRotation().mult(q);
            boxGeometry.setLocalRotation(r);
        }
        
        Vector3f distance = new Vector3f(1, 1, 0).subtract(boxGeometry.getLocalTranslation());
        boxGeometry.getLocalRotation().multLocal(distance);
        sensor.setLocalTranslation(distance);
        //Utils.createDebugArrow(assetManager, boxGeometry.getLocalTranslation(), distance, rootNode);




        sensor.setLocalRotation(boxGeometry.getLocalRotation());

    }

    private void setupKeys(InputManager inputManager) {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_NUMPAD6));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_NUMPAD8));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left")) {
            left = isPressed;
        }
        if (name.equals("Right")) {
            right = isPressed;
        }
        if (name.equals("Up")) {
            forward = isPressed;
        }
        if (name.equals("Down")) {
            backward = isPressed;
        }
    }
}