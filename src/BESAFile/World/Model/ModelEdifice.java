/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.World.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class ModelEdifice {
    
    protected ModelFloor[] floors;
    protected int nFlooors;
    protected int width;
    protected int length;

    public ModelEdifice(ModelEdifice me) {
        this.floors=me.getFloors().clone();
        this.nFlooors=me.getnFlooors();
        this.length=me.getLength();
        this.width=me.getWidth();
    }
    
    
    
    public ModelEdifice(int width,int length,int nFlooors,boolean withNull){
            this.nFlooors=nFlooors;
            this.width=width;
            this.length=length;
            floors=new ModelFloor[nFlooors];
            for (int i=0;i<nFlooors;i++){
                floors[i]=new ModelFloor(width, length,withNull);
            }
    }
    
    public ModelFloor getFloor(int idFloor){
            return floors[idFloor];
    } 

    public int getPostGridFloor(int idFloor,int i,int j){
            return floors[idFloor].get(i, j);
    } 

    public void setFloor(int idFloor,ModelFloor floor){
            floors[idFloor].copyFloorArry(floor.getFloor());
    }
    
    public void setFloorArry(int idFloor,int [][]floor){
            floors[idFloor].copyFloorArry(floor);
    }
    
    public void setPostGridFloor(int idFloor,int i,int j,int value){
            floors[idFloor].set(i, j,value);
    } 

    public ModelFloor[] getFloors() {
        return floors;
    }

    public void setFloors(ModelFloor[] floors) {
        this.floors = floors;
    }

    public int getnFlooors() {
        return nFlooors;
    }

    public void setnFlooors(int nFlooors) {
        this.nFlooors = nFlooors;
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
    
    
    
    public String toString(){
        String edificeS="";
        for (int i=0;i<nFlooors;i++){
            edificeS+=" Floor: "+i+"\n"+floors[i].toString()+"\n";
        }
        return edificeS;
    }
    
    public void addObject(int idFloor,int i,int j,char value){
        floors[idFloor].addObject(i, j,value);
    }

    
    
    
}
