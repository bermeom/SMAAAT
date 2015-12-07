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
    
    
    protected int[][]floor;
    protected int width;
    protected int length;
    public static final int null_=-(1<<32-2);  
    
    public ModelFloor(int width,int length,boolean withNull){
        this.width=width;
        this.length=length;
        floor=new int[width][length];
        for (int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                if(withNull){
                    floor[i][j]=null_;
                }else {
                        floor[i][j]=0;
                }
            }
        }
    }
    
    public int get(int i,int j){
        return floor[i][j];
    }

    public void set(int i,int j,int value){
        floor[i][j]=value;
    }
    
    public void addObject(int i,int j,char value){
        floor[i][j]=value;
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
                if(floor[i][j]==null_)
                    floorS+="\tn";
                else    
                    floorS+="\t"+floor[i][j];
            
            }
            floorS+="\n";
        }
        return floorS;
    }
}
