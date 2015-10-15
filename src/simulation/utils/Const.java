package simulation.utils;

import BESAFile.Agent.Behavior.AgentEnemyMoveGuard;
import BESAFile.Agent.Behavior.AgentProtectorMoveGuard;
import BESAFile.Agent.Behavior.AgentExplorerMoveGuard;
import BESAFile.Agent.Behavior.AgentHostageMoveGuard;

import java.util.HashMap;
import java.util.Map;

public final class Const {
    public static final String EnemyAgent = "EnemyAgent";
    public static final String ExplorerAgent = "ExplorerAgent";
    public static final String HostageAgent = "HostageAgent";
    public static final String GuardianAgent = "GuardianAgent";
    public static final String Character = "Character";
    public static final String Exit = "Exit";
    public static final String World = "WORLD";
    public static final float distBetweenFloors=5;
    public static final float x=0;
    public static final float y=0;
    public static final float z=0;
    public static final int width=10;
    public static final int length=10;
    public static final int nFloors=1;
    public static final long sleep=300;
    
    public static int getType(String name) {
        if(name.contains(Const.GuardianAgent)){
            return 1;
        }
        if(name.contains(Const.ExplorerAgent)){
            return 2;
        }
        if(name.contains(Const.HostageAgent)){
            return 3;
        }
        if(name.contains(Const.EnemyAgent)){
            return 4;
        }
        return 0;
    }
    
    public static Class getGuardMove(int type){
        Class class_=null;
        switch(type){
            case(1):class_= AgentProtectorMoveGuard.class; break;
            case(2):class_=AgentExplorerMoveGuard.class;break;
            case(3):class_=AgentHostageMoveGuard.class;break;
            case(4):class_=AgentEnemyMoveGuard.class;break;
        
        };    
        return class_;
    }
    
    public static  int post(int i){
        return i*2+1;
    }
    
    //x'=x+width-post(i) 
    public static  int coordModel(float xV,float x, float k){
        System.out.println(xV+" "+x+" "+k+" "+(-(xV-x-k)-1));
        return (int)((-(xV-x-k)-1)/2);
    }
     
}
