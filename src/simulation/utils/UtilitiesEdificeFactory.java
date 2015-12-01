/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.utils;

import BESAFile.World.Model.ModelEdifice;

/**
 *
 * @author berme_000
 */
public class UtilitiesEdificeFactory {
    
    public  static ModelEdifice createEdifice(int width,int length,int nFloors){
        ModelEdifice mEdifice=new ModelEdifice(width, length, nFloors,false);
        for (int i=0;i<nFloors;i++){
           
            mEdifice.setPostGridFloor(i,7 ,1, -1);
            mEdifice.setPostGridFloor(i,0,0, -1);            
            mEdifice.setPostGridFloor(i,0,1, -1);            
            mEdifice.setPostGridFloor(i,1,1, -1);            
            mEdifice.setPostGridFloor(i,0,2, -1);            
            mEdifice.setPostGridFloor(i,1,0, -1);
            mEdifice.setPostGridFloor(i,2,0, -1);
            mEdifice.setPostGridFloor(i,3,1, -1);
            mEdifice.setPostGridFloor(i,3,0, -1);
            mEdifice.setPostGridFloor(i,4,4, -1);
            mEdifice.setPostGridFloor(i,3,4, -1);
            mEdifice.setPostGridFloor(i,5,4, -1);
            mEdifice.setPostGridFloor(i,6,4, -1);
            mEdifice.setPostGridFloor(i,6,3, -1);
            mEdifice.setPostGridFloor(i,6,2, -1);
            mEdifice.setPostGridFloor(i,6,1, -1);
            mEdifice.setPostGridFloor(i,2,4, -1);
            mEdifice.setPostGridFloor(i,1,4, -1);
            mEdifice.setPostGridFloor(i,0,4, -1);
            mEdifice.setPostGridFloor(i,0,6, -1);
            mEdifice.setPostGridFloor(i,1,6, -1);
            mEdifice.setPostGridFloor(i,1,7, -1);
            mEdifice.setPostGridFloor(i,1,8, -1);
            mEdifice.setPostGridFloor(i,2,8, -1);
            mEdifice.setPostGridFloor(i,3,8, -1);
            mEdifice.setPostGridFloor(i,4,8, -1);
            mEdifice.setPostGridFloor(i,5,8, -1);
            mEdifice.setPostGridFloor(i,5,6, -1);
            mEdifice.setPostGridFloor(i,5,7, -1);
            mEdifice.setPostGridFloor(i,3,5, -1);
            mEdifice.setPostGridFloor(i,3,6, -1);
            
            //*/
        }
        if(nFloors>1){
            mEdifice.setPostGridFloor(0,8,0, -3);
            mEdifice.setPostGridFloor(1,8,0, -4);
            if(nFloors>2){
                mEdifice.setPostGridFloor(1,9,0, -3);
                mEdifice.setPostGridFloor(2,9,0, -4);
            
            
            }
        }
        
        return mEdifice;
    }
    
}
