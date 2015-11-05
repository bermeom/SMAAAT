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
public class ModelFloor {
    
    
    private int[][]floor;
    private List<ModelObjectWorld> objects;
    private int width;
    private int length;
        
    public ModelFloor(int width,int length){
        this.width=width;
        this.length=length;
        floor=new int[width][length];
        for (int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                floor[i][j]=0;
            }
        }
        objects=new ArrayList<ModelObjectWorld>();
    }
    
    public int get(int i,int j){
        return floor[i][j];
    }

    public void set(int i,int j,int value){
        floor[i][j]=value;
    }
    
    public void addObject(int i,int j,char value){
        floor[i][j]=value;
        objects.add(new ModelObjectWorld(j, j, value));
    }
    
    public void copyFloorArry(int [][] f){
        for (int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                floor[i][j]=f[i][j];
            }
        }
    }

    public int[][] getFloor() {
        return floor;
    }

    public void setFloor(int[][] floor) {
        this.floor = floor;
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
        String floorS="";
        for (int i=0;i<width;i++){
            for (int j=0;j<length;j++){
                floorS+="\t"+floor[i][j];
            }
            floorS+="\n";
        }
        return floorS;
    }
}
