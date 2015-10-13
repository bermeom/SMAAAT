/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World.Model;

/**
 *
 * @author berme_000
 */
public class ModelFloor {
    
    
    private char[][]floor;
    private int width;
    private int length;
        
    public ModelFloor(int width,int length){
        this.width=width;
        this.length=length;
        floor=new char[width][length];
        for (int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                floor[i][j]='0';
            }
        }
    }
    
    public char get(int i,int j){
        return floor[i][j];
    }
    public void set(int i,int j,char value){
        floor[i][j]=value;
    }
    
    public void copyFloorArry(char [][] f){
        for (int i=0;i<width;i++){
            for(int j=0;j<length;j++){
                floor[i][j]=f[i][j];
            }
        }
    }

    public char[][] getFloor() {
        return floor;
    }

    public void setFloor(char[][] floor) {
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
                floorS+=floor[i][j]+" ";
            }
            floorS+="\n";
        }
        return floorS;
    }
}
