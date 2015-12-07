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
        
        
        for (int i=1;i<nFloors;i++){
           
            
            //
            mEdifice.setPostGridFloor(i,7 ,0, -1);
            mEdifice.setPostGridFloor(i,7,1, -1);            
            mEdifice.setPostGridFloor(i,7,2, -1);            
            mEdifice.setPostGridFloor(i,7,3, -1);            
            mEdifice.setPostGridFloor(i,7,4, -1);            
            
            
            mEdifice.setPostGridFloor(i,0,8, -1);            
            mEdifice.setPostGridFloor(i,1,8, -1);            
            mEdifice.setPostGridFloor(i,2,8, -1);            
            mEdifice.setPostGridFloor(i,3,8, -1);            
            mEdifice.setPostGridFloor(i,4,8, -1);            
            mEdifice.setPostGridFloor(i,5,8, -1);            
            mEdifice.setPostGridFloor(i,6,8, -1);            
            
            mEdifice.setPostGridFloor(i,7 ,8, -1);
            mEdifice.setPostGridFloor(i,7,9, -1);            
            mEdifice.setPostGridFloor(i,7,10, -1);            
            mEdifice.setPostGridFloor(i,7,11, -1);            
            mEdifice.setPostGridFloor(i,7,12, -1);            
            
            mEdifice.setPostGridFloor(i,0,15, -1);            
            mEdifice.setPostGridFloor(i,1,15, -1);            
            mEdifice.setPostGridFloor(i,2,15, -1);            
            mEdifice.setPostGridFloor(i,3,15, -1);            
            mEdifice.setPostGridFloor(i,4,15, -1);            
            mEdifice.setPostGridFloor(i,5,15, -1);            
            mEdifice.setPostGridFloor(i,6,15, -1);            
            mEdifice.setPostGridFloor(i,7,15, -1);            
            mEdifice.setPostGridFloor(i,8,15, -1);            
            mEdifice.setPostGridFloor(i,9,15, -1);            
            mEdifice.setPostGridFloor(i,10,15, -1);            
            mEdifice.setPostGridFloor(i,11,15, -1); 
            
            mEdifice.setPostGridFloor(i,0,20, -1);            
            mEdifice.setPostGridFloor(i,1,20, -1);            
           
            mEdifice.setPostGridFloor(i,5,20, -1);            
            mEdifice.setPostGridFloor(i,6,20, -1);            
            mEdifice.setPostGridFloor(i,7,20, -1);            
            mEdifice.setPostGridFloor(i,8,20, -1);            
            mEdifice.setPostGridFloor(i,6,21, -1);            
            mEdifice.setPostGridFloor(i,6,22, -1);            
            mEdifice.setPostGridFloor(i,6,23, -1);            
            mEdifice.setPostGridFloor(i,6,24, -1);            
            mEdifice.setPostGridFloor(i,6,27, -1);            
            mEdifice.setPostGridFloor(i,6,28, -1);            
            mEdifice.setPostGridFloor(i,6,29, -1);            
            
            
            mEdifice.setPostGridFloor(i,9,20, -1);            
            mEdifice.setPostGridFloor(i,10,20, -1);            
            mEdifice.setPostGridFloor(i,11,20, -1); 
            
            mEdifice.setPostGridFloor(i,11,19, -1); 
            mEdifice.setPostGridFloor(i,11,18, -1); 
            mEdifice.setPostGridFloor(i,12,18, -1); 
            mEdifice.setPostGridFloor(i,13,18, -1); 
            
            mEdifice.setPostGridFloor(i,17,18, -1); 
            mEdifice.setPostGridFloor(i,18,18, -1); 
            mEdifice.setPostGridFloor(i,19,18, -1); 
            mEdifice.setPostGridFloor(i,20,18, -1); 
            mEdifice.setPostGridFloor(i,21,18, -1); 
            mEdifice.setPostGridFloor(i,22,18, -1); 
            
            
            mEdifice.setPostGridFloor(i,11,21, -1); 
            mEdifice.setPostGridFloor(i,11,22, -1); 
            mEdifice.setPostGridFloor(i,11,23, -1); 
            
            mEdifice.setPostGridFloor(i,12,23, -1); 
            
            
            mEdifice.setPostGridFloor(i,15,22, -1); 
            mEdifice.setPostGridFloor(i,16,22, -1); 
            mEdifice.setPostGridFloor(i,17,22, -1); 
            mEdifice.setPostGridFloor(i,18,22, -1); 
            mEdifice.setPostGridFloor(i,19,22, -1); 
            mEdifice.setPostGridFloor(i,20,22, -1); 
            mEdifice.setPostGridFloor(i,21,22, -1); 
            mEdifice.setPostGridFloor(i,22,22, -1); 
          
            mEdifice.setPostGridFloor(i,22,21, -1); 
            
            mEdifice.setPostGridFloor(i,18,23, -1); 
            mEdifice.setPostGridFloor(i,18,24, -1); 
            
            
            mEdifice.setPostGridFloor(i,22,23, -1); 
            
            
            mEdifice.setPostGridFloor(i,12,27, -1); 
            
            mEdifice.setPostGridFloor(i,15,27, -1); 
            mEdifice.setPostGridFloor(i,16,27, -1); 
            mEdifice.setPostGridFloor(i,17,27, -1); 
            mEdifice.setPostGridFloor(i,18,27, -1); 
            mEdifice.setPostGridFloor(i,19,27, -1); 
            mEdifice.setPostGridFloor(i,20,27, -1); 
            mEdifice.setPostGridFloor(i,21,27, -1); 
            mEdifice.setPostGridFloor(i,22,27, -1); 
            
            
            mEdifice.setPostGridFloor(i,11,24, -1); 
            mEdifice.setPostGridFloor(i,11,25, -1); 
            mEdifice.setPostGridFloor(i,11,26, -1); 
            mEdifice.setPostGridFloor(i,11,27, -1); 
            
            mEdifice.setPostGridFloor(i,25,27, -1); 
            mEdifice.setPostGridFloor(i,26,27, -1); 
            mEdifice.setPostGridFloor(i,27,27, -1); 
            
            mEdifice.setPostGridFloor(i,27,22, -1); 
            mEdifice.setPostGridFloor(i,27,23, -1); 
            mEdifice.setPostGridFloor(i,27,24, -1); 
            mEdifice.setPostGridFloor(i,27,25, -1); 
            mEdifice.setPostGridFloor(i,27,26, -1); 
            
            
            
            
            //
            mEdifice.setPostGridFloor(i,13 ,0, -1);
            mEdifice.setPostGridFloor(i,13,1, -1);            
            mEdifice.setPostGridFloor(i,13,2, -1);            
            mEdifice.setPostGridFloor(i,13,3, -1);            
            mEdifice.setPostGridFloor(i,13,4, -1);
            
            mEdifice.setPostGridFloor(i,13 ,8, -1);
            mEdifice.setPostGridFloor(i,13,9, -1);            
            mEdifice.setPostGridFloor(i,13,10, -1);            
            mEdifice.setPostGridFloor(i,13,11, -1);            
            mEdifice.setPostGridFloor(i,13,12, -1);
            
            mEdifice.setPostGridFloor(i,16,12, -1);
            mEdifice.setPostGridFloor(i,17,12, -1);
            mEdifice.setPostGridFloor(i,18,12, -1);
            mEdifice.setPostGridFloor(i,19,12, -1);
            mEdifice.setPostGridFloor(i,20,12, -1);
            mEdifice.setPostGridFloor(i,21,12, -1);
            mEdifice.setPostGridFloor(i,22,12, -1);
            
            
            //
            mEdifice.setPostGridFloor(i,19 ,0, -1);
            mEdifice.setPostGridFloor(i,19,1, -1);            
            mEdifice.setPostGridFloor(i,19,2, -1);            
            mEdifice.setPostGridFloor(i,19,3, -1);            
            mEdifice.setPostGridFloor(i,19,4, -1);            
            
            mEdifice.setPostGridFloor(i,19 ,8, -1);
            mEdifice.setPostGridFloor(i,19,9, -1);            
            mEdifice.setPostGridFloor(i,19,10, -1);            
            mEdifice.setPostGridFloor(i,19,11, -1);            
            
            //
            mEdifice.setPostGridFloor(i,25 ,0, -1);
            mEdifice.setPostGridFloor(i,25,1, -1);  
            
            mEdifice.setPostGridFloor(i,25,4, -1);
            mEdifice.setPostGridFloor(i,25,5, -1);  
            mEdifice.setPostGridFloor(i,25,6, -1);  
            mEdifice.setPostGridFloor(i,25,7, -1);  
            mEdifice.setPostGridFloor(i,25,8, -1);  
            mEdifice.setPostGridFloor(i,25,9, -1);  
            mEdifice.setPostGridFloor(i,25,10, -1); 
            mEdifice.setPostGridFloor(i,25,11, -1); 
            mEdifice.setPostGridFloor(i,25,12, -1); 
            mEdifice.setPostGridFloor(i,25,13, -1); 
            mEdifice.setPostGridFloor(i,25,14, -1);    
            mEdifice.setPostGridFloor(i,25,15, -1); 
            mEdifice.setPostGridFloor(i,25,16, -1); 
            mEdifice.setPostGridFloor(i,25,17, -1);    
            mEdifice.setPostGridFloor(i,25,18, -1); 
            mEdifice.setPostGridFloor(i,25,19, -1); 
            
            mEdifice.setPostGridFloor(i,28,19, -1);   
            mEdifice.setPostGridFloor(i,29,19, -1);   

            mEdifice.setPostGridFloor(i,26,11, -1);   
            mEdifice.setPostGridFloor(i,27,11, -1);   
          
            mEdifice.setPostGridFloor(i,26,6, -1);   
            mEdifice.setPostGridFloor(i,27,6, -1);   
            
            mEdifice.setPostGridFloor(i,27,7, -1);   
            mEdifice.setPostGridFloor(i,27,8, -1);   
            
            mEdifice.setPostGridFloor(i,28,6, -1);   

            mEdifice.setPostGridFloor(i,28,8, -1);   
            mEdifice.setPostGridFloor(i,28,9, -1);   
            mEdifice.setPostGridFloor(i,29,9, -1);
            
            mEdifice.setPostGridFloor(i,25,5, -1);   
            mEdifice.setPostGridFloor(i,24,5, -1);   
            mEdifice.setPostGridFloor(i,23,5, -1);  
            
            mEdifice.setPostGridFloor(i,24,22, -1);
            mEdifice.setPostGridFloor(i,25,22, -1);
            mEdifice.setPostGridFloor(i,24,23, -1);   
            

        
        }
        if(nFloors>1){
            
            mEdifice.setPostGridFloor(0,0,29, -3);
            mEdifice.setPostGridFloor(1,0,29, -4);

            mEdifice.setPostGridFloor(0,1,29, -3);
            mEdifice.setPostGridFloor(1,1,29, -4);

            mEdifice.setPostGridFloor(0,29,0, -3);
            mEdifice.setPostGridFloor(1,29,0, -4);

            mEdifice.setPostGridFloor(0,29,1, -3);
            mEdifice.setPostGridFloor(1,29,1, -4);

            /// floor 0
            mEdifice.setPostGridFloor(0,26 ,0, -1);
            mEdifice.setPostGridFloor(0,26 ,1, -1);
            mEdifice.setPostGridFloor(0,26 ,2, -1);
            mEdifice.setPostGridFloor(0,26 ,3, -1);
            mEdifice.setPostGridFloor(0,26 ,4, -1);
            mEdifice.setPostGridFloor(0,26 ,5, -1);
            mEdifice.setPostGridFloor(0,27 ,5, -1);
            mEdifice.setPostGridFloor(0,28 ,5, -1);
            mEdifice.setPostGridFloor(0,29 ,5, -1);
            
            mEdifice.setPostGridFloor(0,0 ,26, -1);
            mEdifice.setPostGridFloor(0,1 ,26, -1);
            mEdifice.setPostGridFloor(0,2 ,26, -1);
            mEdifice.setPostGridFloor(0,3 ,26, -1);
            mEdifice.setPostGridFloor(0,4 ,26, -1);
            mEdifice.setPostGridFloor(0,5 ,26, -1);
            mEdifice.setPostGridFloor(0,5 ,27, -1);
            mEdifice.setPostGridFloor(0,5 ,28, -1);
            mEdifice.setPostGridFloor(0,5 ,29, -1);

            if(nFloors>2){
                mEdifice.setPostGridFloor(1,9,0, -3);
                mEdifice.setPostGridFloor(2,9,0, -4);
            }
        }
        
        return mEdifice;
    }
    
}
