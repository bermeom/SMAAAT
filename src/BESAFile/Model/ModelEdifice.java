/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class ModelEdifice {
    
    private ModelFloor[] floors;
    private int nFlooors;
    
    public ModelEdifice(int width,int length,int nFlooors){
            this.nFlooors=nFlooors;
            floors=new ModelFloor[nFlooors];
            for (int i=0;i<nFlooors;i++){
                floors[i]=new ModelFloor(width, length);
            }
    }
    
    public ModelFloor getFloor(int idFloor){
            return floors[idFloor];
    } 

    public char getPostGridFloor(int idFloor,int i,int j){
            return floors[idFloor].get(i, j);
    } 

    public void setFloor(int idFloor,ModelFloor floor){
            floors[idFloor].copyFloorArry(floor.getFloor());
    }
    
    public void setFloorArry(int idFloor,char [][]floor){
            floors[idFloor].copyFloorArry(floor);
    }
    
    public void setPostGridFloor(int idFloor,int i,int j,char value){
            floors[idFloor].set(i, j,value);
    } 
    
    public String toString(){
        String edificeS="";
        for (int i=0;i<nFlooors;i++){
            edificeS+=" Floor: "+i+"\n"+floors[i].toString()+"\n";
        }
        return edificeS;
    }
    
    
}
