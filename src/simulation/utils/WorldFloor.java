/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;

/**
 *
 * @author berme_000
 */
public class WorldFloor {
    private AssetManager assetManager;
    private int width;
    private int length;
    private float high;
    private float x;
    private float y;
    private float z;
    
    public WorldFloor(AssetManager assetManager, int width, int length, float x, float y, float z) {
        this.assetManager = assetManager;
        this.width = width/Const.kGrid;
        this.length = length/Const.kGrid;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
         Grid grid=new Grid((Const.kGrid*this.length+1),(Const.kGrid*this.width+1), 2/Const.kGrid);
         Geometry g = new Geometry("GridFloor", grid );
         Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat.getAdditionalRenderState().setWireframe(true);
         mat.setColor("Color", color);
         g.setMaterial(mat);
         g.center().move(new Vector3f(x,y+0.205f,z));
         return g;
    }    
    public Geometry makeFloor() {
        Box box = new Box(width, .2f, length);
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
        Box box = new Box(.2f,high+.2f, length);
        Geometry floor = new Geometry("WallFloor1", box);
        floor.setLocalTranslation(x+width+0.2f, y+high, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }
    
    public Geometry makeWallFloor2() {
        Box box = new Box(.2f,high+.2f, length);
        Geometry floor = new Geometry("WallFloor2", box);
        floor.setLocalTranslation(x-width-0.2f, y+high, z);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }
    
    public Geometry makeWallFloor3() {
        Box box = new Box(width+.4f,high+.2f, .2f);
        Geometry floor = new Geometry("WallFloor3", box);
        floor.setLocalTranslation(x, y+high, z-length-.2f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.getAdditionalRenderState().setWireframe(true);
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }

    public Geometry makeWallFloor4() {
        Box box = new Box(width+.4f,high+.2f, .2f);
        Geometry floor = new Geometry("WallFloor4", box);
        floor.setLocalTranslation(x, y+high, z+length+.2f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture2.jpg"));
        mat1.getAdditionalRenderState().setWireframe(true);
        //mat1.setColor("Color", ColorRGBA.LightGray);
        floor.setMaterial(mat1);
        return floor;
    }

    
    public Geometry makeCubeb(String name,int i,int j,float highb) {
      Box box = new Box(0.5f, highb, 0.5f);
      Geometry cube = new Geometry(name, box);
      cube.setLocalTranslation(x+width-Const.post(i), y+1.2f-(1-highb), z+length-Const.post(j));
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture3.jpg"));
      mat1.setColor("Color", ColorRGBA.Gray);
      cube.setMaterial(mat1);
      return cube;
    }
    
    public Geometry makeCubeB(String name,int i,int j) {
      Box box = new Box(0.5f, 1, 0.5f);
      Geometry cube = new Geometry(name, box);
      cube.setLocalTranslation(x+width-Const.post(i), y+1.2f, z+length-Const.post(j));
        System.out.println(" "+i+" "+j+" "+cube.getLocalTranslation());
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture3.jpg"));
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
      cube.setLocalTranslation(x+width-Const.post(i), y+1.2f, z+length-Const.post(j));
      Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mat1.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/texture3.jpg"));
      mat1.setColor("Color", ColorRGBA.Gray);
      cube.setMaterial(mat1);
      return cube;
    }

    

}
