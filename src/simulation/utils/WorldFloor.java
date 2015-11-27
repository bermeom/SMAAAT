/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.utils;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author berme_000
 */
public class WorldFloor {
    //width  length
    private AssetManager assetManager;
    private float length;
    private float width;
    private float high;
    private float x;
    private float y;
    private float z;
    
    public WorldFloor(AssetManager assetManager, int width, int length, float x, float y, float z) {
        this.assetManager = assetManager;
        this.length = ((float)length)/ (Const.kGrid);
        this.width = ((float)width)/ (Const.kGrid);
        System.out.println("----------------"+this.length+" "+this.width);
        this.x = x;
        this.y = y;
        this.z = z;
        this.high=1.2f;

        
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public float getWidth() {
        return (float)length;
    }

    public void setWidth(float width) {
        this.length = width;
    }

    public float getLength() {
        return (float)width;
    }

    public void setLength(float length) {
        this.width = length;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    public Geometry generateEdifice(){
        Node n=new Node();
        n.attachChild(makeFloor());
        return makeFloor();
    }
    
 
    public Geometry makeGridFloor(ColorRGBA color){
         Grid grid=new Grid((int)((float)Const.kGrid*this.width+1),(int)((float)Const.kGrid*this.length+1),(2/(float)Const.kGrid));
         Geometry g = new Geometry("GridFloor", grid );
         Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat.getAdditionalRenderState().setWireframe(true);
         mat.setColor("Color", color);
         g.setMaterial(mat);
         g.center().move(new Vector3f(x,y+0.205f,z));
         return g;
    }    
    public Geometry makeFloor() {
        Box box = new Box((float)length, .2f, (float)width);
        Geometry floor = new Geometry("Floor", box);
        floor.setLocalTranslation(x, y, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }

    public Geometry makeWallFloor1() {
        Box box = new Box(.2f,(float)high+.2f,(float) width);
        Geometry floor = new Geometry("WallFloor1", box);
        floor.setLocalTranslation(x+(float)length+0.2f, y+(float)high, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        
        return floor;
    }
    
    public Geometry makeWallFloor2() {
        Box box = new Box(.2f,(float)high+.2f, (float)width);
        Geometry floor = new Geometry("WallFloor2", box);
        floor.setLocalTranslation(x-(float)length-0.2f, y+(float)high, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }
    
    public Geometry makeWallFloor3() {
        Box box = new Box((float)length+.4f,(float)high+.2f, .2f);
        Geometry floor = new Geometry("WallFloor3", box);
        floor.setLocalTranslation(x, y+(float)high, z-(float)width-.2f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.getAdditionalRenderState().setWireframe(true);
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }

    public Geometry makeWallFloor4() {
        Box box = new Box((float)length+.4f,(float)high+.2f, .2f);
        Geometry floor = new Geometry("WallFloor4", box);
        floor.setLocalTranslation(x, y+(float)high, z+(float)width+.2f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.getAdditionalRenderState().setWireframe(true);
        //mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }

    
    public Geometry makeCubeb(String name,int i,int j,float highb) {
      Box box = new Box((float)1/Const.kGrid, highb, (float)1/Const.kGrid);
      Geometry cube = new Geometry(name, box);
      cube.setLocalTranslation(x+length-Const.post(j), y+1.2f-(1-highb), z+width-Const.post(i));
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture3.jpg"));
      mat1.setColor("Color", ColorRGBA.Gray);
      cube.setMaterial(mat1);
      return cube;
    }
    
    public Geometry makeCubeB(String name,int i,int j) {
      Box box = new Box((float)1/Const.kGrid, (float)high-0.2f,(float)1/Const.kGrid);
      Geometry cube = new Geometry(name, box);
      cube.setLocalTranslation(x+length-Const.post(j), y+1.2f, z+width-Const.post(i));
      //System.out.println(" "+i+" "+j+" "+cube.getLocalTranslation());
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      TextureKey key = new TextureKey("Textures/texture6.jpg");
      key.setGenerateMips(true);
      key.setTextureTypeHint(Texture.Type.CubeMap);
      Texture tex = assetManager.loadTexture(key);
      mat1.setTexture("ColorMap", tex);
      mat1.setColor("Color", ColorRGBA.Gray);
      cube.setMaterial(mat1);
      //cube.scale(0.5)
      return cube;
    }
    
    /** A cube object for target practice */
    public Geometry makeWall(String name, int i,int j,boolean sentido) {
      Box box;
      if(sentido){
          box = new Box(1, 1, 0.5f);
      }else{
            box = new Box(0.5f, 1, 1);
      }
      Geometry cube = new Geometry(name, box);
      cube.setLocalTranslation(x+(float)length-Const.post(i), y+1.2f, z+(float)width-Const.post(j));
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture3.jpg"));
      mat1.setColor("Color", ColorRGBA.Gray);
      cube.setMaterial(mat1);
      return cube;
    }

    

}
