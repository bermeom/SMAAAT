package simulation.utils;

import BESAFile.Agent.State.Position;
import BESAFile.Data.Vector3D;
import BESAFile.Model.SeenObject;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class Utils {

    public static Geometry createDebugArrow(AssetManager assetManager, Vector3f pos, Vector3f dir, Node node) {
        Arrow arrow = new Arrow(Vector3f.UNIT_Z.mult(dir.length()));
        arrow.setLineWidth(3);
        Geometry mark = new Geometry("DebugArrow", arrow);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setDepthTest(false);
        mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mat);
        mark.setLocalTranslation(pos);

        Quaternion q = new Quaternion();
        q.lookAt(dir, Vector3f.UNIT_Y);
        mark.setLocalRotation(q);

        if (node != null) {
            node.attachChild(mark);
        }
        return mark;
    }

    public static Geometry createDebugBox(AssetManager assetManager, Vector3f pos, float side, Node node) {
        Box s = new Box(side, side, side);
        Geometry mark = new Geometry("DebugBox", s);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setDepthTest(false);
        mat.setColor("Color", ColorRGBA.Blue);
        mark.setMaterial(mat);
        mark.setLocalTranslation(pos);
        if (node != null) {
            node.attachChild(mark);
        }
        return mark;
    }

    public static Geometry createDebugSphere(AssetManager assetManager, Vector3f pos, float radius, Node node) {
        Sphere s = new Sphere(10, 10, radius);
        Geometry mark = new Geometry("DebugSphere", s);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setDepthTest(false);
        mat.setColor("Color", ColorRGBA.Green);
        mark.setMaterial(mat);
        mark.setLocalTranslation(pos);
        if (node != null) {
            node.attachChild(mark);
        }
        return mark;
    }

    public static int randomInteger(int min, int max) {
        XSRandom rand = new XSRandom();
        int randomNum ;
        do{
            randomNum = rand.nextInt((max - min) + 1) + min;
        }while(randomNum<min||randomNum>max);
        return randomNum;
    }
    
    public static int randomIntegerMA(int min, int max) {
        
        Random random=new Random(System.currentTimeMillis()*randomInteger(0, 100));
        int randomNum =0;
        do{
            randomNum = random.nextInt((max - min) + 1) + min;
        }while(randomNum<min||randomNum>max);
        return randomNum;
    }
    
    public static Vector3D direction(int i,int j){
            int x,z;
            if (i == 1){
                z=0;
                if (j == 0){
                    x=-1;
                    }else {
                            x=1;
                        }
                }else if (j == 1){
                        x=0;
                        if (j == 0){
                            z=-1;
                            }else{
                                    z=1;
                                }
                        }else{
                                if(i==0&&j==2){
                                    z=-1;
                                    x=1;
                                }else if (i==2&&j==0){
                                            z=1;
                                            x=-1;
                                        }else{
                                            x=i-1;
                                            z=j-1;
                                            }

                            }
            return (new Vector3D(x, 0, z));
        
    }
    
    public static int[][] border(int [][]mat, Position p, int width,int length ,int tam){
        
        int x=tam/2;
        /*
        if(p.getYpos()+1>=width){
            for(int i=0;i<tam;i++){
                if(mat[i][x+1]==0)
                    mat[i][x+1]=-1;
            }
        }
        if(p.getYpos()-1<0){
            for(int i=0;i<tam;i++){
                if(mat[i][x-1]==0)
                    mat[i][x-1]=-1;
            }
        }
        if(p.getXpos()+1>=length){
            for(int i=0;i<tam;i++){
                if(mat[x+1][i]==0)
                    mat[x+1][i]=-1;
            }
        }
        if(p.getXpos()-1<0){
            for(int i=0;i<tam;i++){
                if(mat[x-1][i]==0)
                    mat[x-1][i]=-1;
            }
        }
        */
        
        if(mat[x-1][x]!=0&&mat[x-1][x]!=-4&&mat[x-1][x]!=-3){
            for(int i=0;i<tam;i++){
                if(mat[x-1][i]==0)
                    mat[x-1][i]=-1;
            }
        }
        if(mat[x+1][x]!=0&&mat[x+1][x]!=-4&&mat[x+1][x]!=-3){
            for(int i=0;i<tam;i++){
                if(mat[x+1][i]==0)
                    mat[x+1][i]=-1;
            }
        }
        if(mat[x][x-1]!=0&&mat[x][x-1]!=-4&&mat[x][x-1]!=-3){
            for(int i=0;i<tam;i++){
                if(mat[i][x-1]==0)
                    mat[i][x-1]=-1;
            }
        }
        if(mat[x][x+1]!=0&&mat[x][x+1]!=-4&&mat[x][x+1]!=-3){
            for(int i=0;i<tam;i++){
                if(mat[i][x+1]==0)
                    mat[i][x+1]=-1;
            }
        }
        return mat;
    }
    
    public static SeenObject selectRandomSeenObjects(List<SeenObject> seenObjects){
            if(seenObjects.size()>0){
                int n=Utils.randomIntegerMA(0, seenObjects.size()-1);
                return  seenObjects.get(n);
            }
            return null;
    }
    
}
