/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Data.Vector3D;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelFloor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author berme_000
 */
public class AgentState extends  StateBESA{
        
    protected ModelEdifice edifice;
    protected Position position;
    protected  String alias;
    protected Vector3D direction;
    protected double radius;
    protected double height;
    protected int sightRange;
    protected  Queue<Vector3D> possibleMotions;
    protected int type;
    protected double speed;
    protected boolean[] consecutiveMSN;
    protected int nextConsecutive;
    protected int contMessagesOld;
    protected int limitContMessagesOld;
    protected Position goal;
    protected Motion motion; 
    protected int movX[]={1,0, 0,-1,-1,1,-1, 1};
    protected int movY[]={0,1,-1, 0,-1,1, 1,-1};
    protected ModelFloor gridWeights;
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
        this.goal=new Position();
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = radius*2;
        this.sightRange = 2;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors,true);
        this.edifice.setPostGridFloor(idfloor,xpos,ypos,0);
        this.speed=3;
        this.consecutiveMSN=new boolean[1000];
        this.nextConsecutive=0;
        this.contMessagesOld=0;
        this.limitContMessagesOld=5;
        this.motion=new Motion();
        
    }
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
        this.goal=new Position();
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = height;
        this.sightRange = 2;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors,true);
        this.edifice.setPostGridFloor(idfloor,xpos,ypos,0);
        this.speed=1;
        this.consecutiveMSN=new boolean[1000];
        this.nextConsecutive=0;
         this.nextConsecutive=0;
        this.contMessagesOld=0;
        this.limitContMessagesOld=5;
        this.motion=new Motion();
     }

    public ModelEdifice getEdifice() {
        return edifice;
    }

    public void setEdifice(ModelEdifice edifice) {
        this.edifice = edifice;
    }

    public int getXpos() {
        return this.position.getXpos();
    }

    public void setXpos(int xpos) {
       this.position.setXpos(xpos);
    }

    public int getYpos() {
        return this.position.getYpos();
    }

    public void setYpos(int ypos) {
       this.position.setYpos(ypos);
    }

    public int getIdfloor() {
        return this.position.getIdfloor();
    }

    public void setIdfloor(int idfloor) {
       this.position.setIdfloor(idfloor);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Queue<Vector3D> getPossibleMotions() {
        return possibleMotions;
    }

    public void setPossibleMotions(Queue<Vector3D> possibleMotions) {
        this.possibleMotions = possibleMotions;
    }

    public int getSightRange() {
        return sightRange;
    }

    public void setSightRange(int sightRange) {
        this.sightRange = sightRange;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void setModelEdiffice(int idFloor,int i,int j,int value){
        this.edifice.setPostGridFloor(idFloor, i, j, value);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Motion getMotion() {
        return motion;
    }

    public void setMotion(Motion motion) {
        this.motion = motion;
    }
    
    public int getNextConsecutive(){
        this.nextConsecutive++;
        if(this.nextConsecutive>=this.consecutiveMSN.length){
            this.nextConsecutive=0;
        }
        this.consecutiveMSN[this.nextConsecutive]=true;
        return this.nextConsecutive;
    }
    
    public void marckConsecutive(int idConsecutive){
        this.consecutiveMSN[idConsecutive]=false;
    }
    
    public boolean getIdConsecutive(int idConsecutive){
        return this.consecutiveMSN[idConsecutive];
    }
            
    public void plusPlusContMsOld(){
        this.contMessagesOld++;
    }
    
    public void resetContMsOld(){
        this.contMessagesOld=0;
    }
    
    public boolean isExceededContMsOld(){
            return this.contMessagesOld>=this.limitContMessagesOld;
    }

    public int getContMessagesOld() {
        return contMessagesOld;
    }

    public void setContMessagesOld(int contMessagesOld) {
        this.contMessagesOld = contMessagesOld;
    }
    
    protected Motion getMovementsRandom(List<Motion> motions){
            if(motions.size()>0){
                Random random=new Random(System.currentTimeMillis());
                int n=0;
                do{
                    n= random.nextInt(motions.size());
                }while(n<0 || n>=motions.size());
                return  motions.get(n);
            }
            return new Motion();
    }
    
    protected boolean  intervalValidation(int n,int limit){
        return n>=0&&n<limit;
    }
    
    public boolean nextMotion(List<Motion> movements){
           
           if (!this.goal.isIsNull()&&this.edifice.getPostGridFloor(this.goal.getIdfloor(), this.goal.getXpos(), this.goal.getYpos())==ModelFloor.null_){
               findMotion(movements);
               //System.out.println("1--------------"+this.motion);
            }else{
                   this.motion=new Motion(); 
                   goalSeek();
                   if(!this.goal.isIsNull()){
                       findMotion(movements); 
                    }else{
                       this.motion=getMovementsRandom(movements);
                   }
                   //System.out.println("2--------------"+this.motion);

           }
           //
            return !this.motion.isIsNull();
           
    }
    
    protected void goalSeek(){
                    this.goal=new Position();
                    List<Position> goalsPosibles=new ArrayList<Position>();
                    Deque<Position> bfs = new ArrayDeque<Position>();
                    bfs.add(this.position);
                    Position p=new Position();
                    int newX,newY;
                    this.gridWeights=new ModelFloor(this.edifice.getWidth(), this.edifice.getLength(), true);
                    this.gridWeights.set(this.position.getXpos(), this.position.getYpos(), 0);
                    while (!bfs.isEmpty()){
                        p=bfs.pop();
                        for(int i=0;i<8;i++){
                            newX=movX[i]+p.getXpos();
                            newY=movY[i]+p.getYpos();
                            if (intervalValidation(newX,this.edifice.getWidth())&&intervalValidation(newY,this.edifice.getLength())&&(this.gridWeights.get(newX, newY)!=0 && this.gridWeights.get(newX, newY)!=-1)){
                                if (this.edifice.getPostGridFloor(this.position.getIdfloor(), newX, newY)==ModelFloor.null_){
                                   goalsPosibles.add(new Position(newX, newY, this.position.getIdfloor()));
                                   this.gridWeights.set(newX, newY, -1);
                                }else if (this.edifice.getPostGridFloor(this.position.getIdfloor(), newX, newY)==0){
                                            bfs.add(new Position(newX, newY, this.position.getIdfloor()));
                                            this.gridWeights.set(newX, newY, 0);
                                        } 
                                }
                        }


                    }
                    double minDisctane=-1,de;
                    for(Position p1:goalsPosibles){
                        de=this.position.euclideanDistance(p1);
                        if(minDisctane==-1||minDisctane>de){
                            minDisctane=de;
                            p=p1;
                        }
                    }
                    
                    
                    if (minDisctane!=-1){
                        this.goal=p;
                        waveFront(); 
                        System.out.println(this.goal);
                    }
                   
    
    }

    private void waveFront() {
                    Deque<Position> bfs = new ArrayDeque<Position>();
                    Position p=new Position();
                    bfs.add(this.goal);
                    this.gridWeights.set(this.goal.getXpos(), this.goal.getYpos(), 1);
                    int newX,newY;
                    while (!bfs.isEmpty()){
                        p=bfs.pop();
                        for(int i=0;i<8;i++){
                            newX=movX[i]+p.getXpos();
                            newY=movY[i]+p.getYpos();
                            if (intervalValidation(newX,this.edifice.getWidth())&&intervalValidation(newY,this.edifice.getLength())&&(this.gridWeights.get(newX, newY)==0)){
                                bfs.add(new Position(newX, newY, this.position.getIdfloor()));
                                this.gridWeights.set(newX, newY, this.gridWeights.get(p.getXpos(), p.getYpos())+1);
                            }
                        }
                    }
                    //System.out.println(this.gridWeights);
        
    }

    private void findMotion(List<Motion> movements) {
            double de,minDisctane=-1;//this.gridWeights.get(this.position.getXpos(), this.position.getXpos());
            
            for(Motion m:movements){
                 de=this.gridWeights.get(m.getXpos(), m.getYpos());
                 //System.out.println("->> "+de+" "+m);
                 if(minDisctane==-1||minDisctane>de){
                     minDisctane=de;
                     this.motion=m;
                     }
             }
            //System.out.println(this.motion);
    }
    
    
}
