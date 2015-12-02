/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Data.NegotiationData;
import BESAFile.Data.Vector3D;
import BESAFile.Model.DesiredGoal;
import BESAFile.Model.SeenObject;
import BESAFile.World.Model.ModelEdifice;
import BESAFile.World.Model.ModelFloor;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import simulation.utils.Const;
import simulation.utils.Utils;

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
    //
    protected Deque<DesiredGoal> desiredGoals;
    protected Motion motion; 
    protected int movX[]={1,0, 0,-1,-1,1,-1, 1};
    protected int movY[]={0,1,-1, 0,-1,1, 1,-1};
    protected List<Boolean> fullExplorationFloor;
    protected int nExproationFloor;
    protected boolean deadLock;
    protected boolean winNegotiation;
    protected NegotiationData negotiationData;
    protected boolean changeFloor;
    protected List<List<Position> > climbStairsForFloor;
    protected List<List<Position> > downStairsForFloor;
    protected int life;
    
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
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
        this.fullExplorationFloor=new ArrayList<>();
        this.deadLock=false;
        this.negotiationData=new NegotiationData();
        this.desiredGoals=new ArrayDeque<DesiredGoal>();
        this.changeFloor=false;
        this.downStairsForFloor=new ArrayList<>();
        this.climbStairsForFloor=new ArrayList<>();
        for(int i=0;i<nFlooors;i++){
            this.downStairsForFloor.add(new ArrayList<Position>());
            this.climbStairsForFloor.add(new ArrayList<Position>());
            this.fullExplorationFloor.add(false);
        }    
        this.nExproationFloor=1;
        this.life=10;
    }
    

    
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
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
        this.fullExplorationFloor=new ArrayList<>();
        this.deadLock=false;
        this.negotiationData=new NegotiationData();
        this.desiredGoals=new ArrayDeque<DesiredGoal>();
        this.changeFloor=false;
         for(int i=0;i<nFlooors;i++){
            this.downStairsForFloor.add(new ArrayList<Position>());
            this.climbStairsForFloor.add(new ArrayList<Position>());
            this.fullExplorationFloor.add(false);
        }  
        this.nExproationFloor=1;
        this.life=10;
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
  
    public void setModelEdiffice(int idFloor,int i,int j,int value){
            this.edifice.setPostGridFloor(idFloor, i, j, value);
    }
    
    /*
    public void isOpenPosModelEdiffice(int idFloor,int i,int j,int value){
            this.edifice.setPostGridFloor(idFloor, i, j, value);
    }
    
   
    public void setModelEdiffice(int idFloor,int i,int j,int value){
        this.edifice.setPostGridFloor(idFloor, i, j, value);
    }
    */
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

    public Position getGoal() {
        return this.desiredGoals.getFirst().getGoal();
    }
    
    public int getGoalType() {
        return this.desiredGoals.getFirst().getType();
    }

 
    public ModelFloor getGridWeights() {
        if(this.desiredGoals.isEmpty()){
            return null;
        }
        return this.desiredGoals.getFirst().getGridWeights();
    }

    public Deque<DesiredGoal> getDesiredGoals() {
        return desiredGoals;
    }

    public void setDesiredGoals(Deque<DesiredGoal> desiredGoals) {
        this.desiredGoals = desiredGoals;
    }

    
    
    public boolean isFullExplorationMap(int idFloor) {
        return fullExplorationFloor.get(idFloor);
    }

    public void setFullExplorationMap(int idFloor,boolean fullExplorationMap) {
        this.fullExplorationFloor.set(idFloor,fullExplorationMap);
    }

    public boolean isDeadLock() {
        return deadLock;
    }

    public void setDeadLock(boolean deadLock) {
        this.deadLock = deadLock;
    }
    
    public NegotiationData getNegotiationData() {
        return negotiationData;
    }

    public void setNegotiationData(NegotiationData negotiationData) {
        this.negotiationData = negotiationData;
    }

    public boolean isWinNegotiation() {
        return winNegotiation;
    }

    public void setWinNegotiation(boolean winNegotiation) {
        this.winNegotiation = winNegotiation;
    }

    public boolean isChangeFloor() {
        return changeFloor;
    }

    public void setChangeFloor(boolean changeFloor) {
        this.changeFloor = changeFloor;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public void decreaseLife() {
        this.life--;
    }
    
    
    public Motion getMovementsRandom(List<Motion> motions){
            if(motions.size()>0){
                int n=Utils.randomIntegerMA(0, motions.size()-1);
                return  motions.get(n);
            }
            return new Motion();
    }
    
    protected boolean  intervalValidation(int n,int limit){
        return n>=0&&n<limit;
    }
    
    public boolean nextMotion(List<Motion> movements){
           this.motion.setIsNull(true); 
           if (!this.desiredGoals.isEmpty()&&this.edifice.getPostGridFloor(this.desiredGoals.getFirst().getGoal().getIdfloor(), this.desiredGoals.getFirst().getGoal().getXpos(), this.desiredGoals.getFirst().getGoal().getYpos())==ModelFloor.null_){
               //System.out.println(this.getGridWeights());
               findMotion(movements);
            }else if(!this.desiredGoals.isEmpty()&&(this.getGoalType()==-3||this.getGoalType()==-4)&&!this.position.isEquals(this.getGoal())){
               findMotion(movements);
            }else if(!this.desiredGoals.isEmpty()&&(this.getGoalType()==-3||this.getGoalType()==-4)&&this.position.isEquals(this.getGoal())){
                this.changeFloor=true;
            }else{
                   if(!this.desiredGoals.isEmpty()){
                       this.desiredGoals.pop();
                   }
                   goalSeek();
                   if(!this.desiredGoals.isEmpty()){
                       findMotion(movements); 
                    }else{
                       //this.motion=getMovementsRandom(movements);
                   }
                   
           }
           //
           
            return !this.motion.isIsNull();
           
    }
    
    
    
    protected void goalSeek(){
            if (!this.fullExplorationFloor.get(this.position.idfloor)){
                exploringFloorMapGoal();
            }else{
                // -> Explorin region Goal <-
                /*Find door down*/
                if(this.nExproationFloor==this.edifice.getnFlooors()){
                
                }else if (this.downStairsForFloor.get(this.position.getIdfloor()).size()>0&&this.position.getIdfloor()!=1){
                        int i=0;
                        System.out.println("NEW GOAL ->>>>>>>>>>>>>");
                        this.addGoal(this.downStairsForFloor.get(this.position.getIdfloor()).get(i), true, true, this.edifice.getPostGridFloor(this.downStairsForFloor.get(this.position.getIdfloor()).get(i)));
                        }else if (this.climbStairsForFloor.get(this.position.getIdfloor()).size()>0){
                                int i=0;
                                System.out.println("NEW GOAL ->>>>>>>>>>>>>");
                                this.addGoal(this.climbStairsForFloor.get(this.position.getIdfloor()).get(i), true, true, this.edifice.getPostGridFloor(this.climbStairsForFloor.get(this.position.getIdfloor()).get(i)));
                        } 
                /******************/
            }        
                   
    
    }
    
    private void exploringFloorMapGoal(){
            Deque<Position> goalsPosibles=new ArrayDeque<Position>();
            Deque<Position> bfs = new ArrayDeque<Position>();
            bfs.add(this.position);
            Position p=new Position();
            int newX,newY;
            ModelFloor gridWeights=new ModelFloor(this.edifice.getWidth(), this.edifice.getLength(), true);
            gridWeights.set(this.position.getXpos(), this.position.getYpos(), 0);
            while (!bfs.isEmpty()){
                p=bfs.pop();
                for(int i=0;i<8;i++){
                    newX=movX[i]+p.getXpos();
                    newY=movY[i]+p.getYpos();
                    if (intervalValidation(newX,this.edifice.getWidth())&&intervalValidation(newY,this.edifice.getLength())&&(gridWeights.get(newX, newY)!=0 && gridWeights.get(newX, newY)!=-1)){
                        if (this.edifice.getPostGridFloor(this.position.getIdfloor(), newX, newY)==ModelFloor.null_){
                           goalsPosibles.add(new Position(newX, newY, this.position.getIdfloor()));
                           gridWeights.set(newX, newY, -1);
                        }else if (this.edifice.getPostGridFloor(this.position.getIdfloor(), newX, newY)==0){
                                    bfs.add(new Position(newX, newY, this.position.getIdfloor()));
                                    gridWeights.set(newX, newY, 0);
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
                gridWeights=new ModelFloor(this.edifice.getWidth(), this.edifice.getLength(), true);
                gridWeights.copyFloorArry(this.edifice.getFloor(p.getIdfloor()).getFloor());
                gridWeights=waveFront(p,gridWeights); 
                this.desiredGoals.addLast(new DesiredGoal(p,gridWeights,true,this.edifice.getPostGridFloor(p)));
                //System.out.println(this.goal);
            }else{
                this.nExproationFloor++;
                this.fullExplorationFloor.set(this.position.getIdfloor(), true);
                this.motion.setIsNull(true); 
                goalSeek();
            }
    
    } 
    
    private ModelFloor waveFront(Position goal,ModelFloor gridWeights) {
                    Deque<Position> bfs = new ArrayDeque<Position>();
                    Position p=new Position();
                    bfs.add(goal);
                    gridWeights.set(goal.getXpos(), goal.getYpos(), 1);
                    int newX,newY;
                    while (!bfs.isEmpty()){
                        p=bfs.pop();
                        for(int i=0;i<8;i++){
                            newX=movX[i]+p.getXpos();
                            newY=movY[i]+p.getYpos();
                            if (intervalValidation(newX,this.edifice.getWidth())&&intervalValidation(newY,this.edifice.getLength())&&Const.validationIdGrid(gridWeights.get(newX, newY))){
                                bfs.add(new Position(newX, newY, this.position.getIdfloor()));
                                gridWeights.set(newX, newY, gridWeights.get(p.getXpos(), p.getYpos())+1);
                            }
                        }
                    }
                    //System.out.println(this.gridWeights);
                    return gridWeights;
        
    }

    private void findMotion(List<Motion> movements) {
            int de=ModelFloor.null_,minDisctane=-1;//this.gridWeights.get(this.position.getXpos(), this.position.getXpos());
            this.motion.setIsNull(true); 
            List<Motion> lm=new ArrayList<Motion>();
            for(Motion m:movements){
                 de=this.desiredGoals.getFirst().getGridWeights().get(m.getXpos(), m.getYpos());
                 if(minDisctane==-1||(minDisctane>de&&this.desiredGoals.getFirst().isAttraction())||(minDisctane<de&&!this.desiredGoals.getFirst().isAttraction())){
                     minDisctane=de;
                     }
             }
            if(minDisctane!=-1&&de==ModelFloor.null_){
                Position goal=this.desiredGoals.getFirst().getGoal();
                boolean atractionTem=this.desiredGoals.getFirst().isAttraction();
                ModelFloor gridWeights=new ModelFloor(this.edifice.getWidth(), this.edifice.getLength(), true);
                gridWeights.copyFloorArry(this.edifice.getFloor(goal.getIdfloor()).getFloor());
                gridWeights=waveFront(this.desiredGoals.getFirst().getGoal(), gridWeights);
                this.desiredGoals.pop();
                this.desiredGoals.addFirst(new DesiredGoal(goal, gridWeights,atractionTem,this.edifice.getPostGridFloor(goal)));
                //System.out.println(gridWeights);
                //System.out.println(this.edifice);
                findMotion(movements);
                return;
            }
            
            for(Motion m:movements){
                 de=this.desiredGoals.getFirst().getGridWeights().get(m.getXpos(), m.getYpos());
                 if(minDisctane==de){
                     lm.add(m);
                     }
              }
            this.motion=getMovementsRandom(lm); 
            int p1,p2;
            p1=this.desiredGoals.getFirst().getGridWeights().get(this.position.getXpos(),this.position.getYpos());
            p2=this.desiredGoals.getFirst().getGridWeights().get(this.motion.getXpos(),this.motion.getYpos());
            
            if (movements.size()==1 && ((p1<p2&&this.desiredGoals.getFirst().isAttraction())||(p1>p2&&!this.desiredGoals.getFirst().isAttraction()))){
                //Solucion sofisticada
                //this.motion.setIsNull(true);
                //this.deadLock=true;
                //*/Solucion simple
                movements.add(new Motion(this.position.getXpos(),this.position.getYpos(),this.position.getIdfloor()));
                this.motion=getMovementsRandom(movements); 
                //*/
            }
            //*/
            //System.out.println(this.motion);
    }
    
    
    public SeenObject  solutionDeadLock(List<SeenObject> seenOs, int [][]mat){
        int tam=(int)this.sightRange*2+1;
        int x,y,p1,p2;
        SeenObject agent=null;
        for(int i=0;i<4;i++){
            x=movX[i]+this.position.getXpos();
            y=movY[i]+this.position.getYpos();
            if (intervalValidation(x,this.edifice.getWidth())&&intervalValidation(y,this.edifice.getLength())&&
                 (this.desiredGoals.getFirst().getGridWeights().get(x, y)>0&& 
                      ((this.desiredGoals.getFirst().getGridWeights().get(x, y)<this.desiredGoals.getFirst().getGridWeights().get(position.getXpos(), position.getYpos())&&this.desiredGoals.getFirst().isAttraction())
                        ||(this.desiredGoals.getFirst().getGridWeights().get(x, y)>this.desiredGoals.getFirst().getGridWeights().get(position.getXpos(), position.getYpos())&&!this.desiredGoals.getFirst().isAttraction())
                        )
                    )){
                agent=seenOs.get(mat[tam/2+movX[i]][tam/2+movY[i]]-1);
                break;
            }
        }
        return agent;
    
    }

    public void addGoal(Position goal,boolean attraction,boolean first,int type){
        
        ModelFloor gridWeights=new ModelFloor(this.edifice.getWidth(), this.edifice.getLength(), true);
        gridWeights.copyFloorArry(this.edifice.getFloor(goal.getIdfloor()).getFloor());
        gridWeights=waveFront(goal,gridWeights); 
        //System.out.println(gridWeights);
        if(first){
            this.desiredGoals.addFirst(new DesiredGoal(goal, gridWeights, attraction,type));
        }else{
            this.desiredGoals.addLast(new DesiredGoal(goal, gridWeights, attraction,type));
        }
    }
    
    public DesiredGoal getDesiredGoal(){
        return this.desiredGoals.getFirst();
    }
    
    
    public void addDownStairs(int idfloor,int xPos,int yPos){
        if(this.edifice.getPostGridFloor(idfloor, xPos, yPos)==ModelFloor.null_){
            //System.out.println("DOWN STAIRS  .>>>> "+xPos+" "+yPos+" "+this.edifice.getPostGridFloor(idfloor, yPos, yPos));
            this.downStairsForFloor.get(idfloor).add(new Position(xPos, yPos, idfloor));
        }
    }
    
    public void addClimbtairs(int idfloor,int xPos,int yPos){
        if(this.edifice.getPostGridFloor(idfloor, xPos, yPos)==ModelFloor.null_){
            //System.out.println("CLIMB STAIRS  .>>>> "+xPos+" "+yPos+" "+this.edifice.getPostGridFloor(idfloor, yPos, yPos));
            this.climbStairsForFloor.get(idfloor).add(new Position(xPos, yPos, idfloor));
        }
    }
    
    
    
}
