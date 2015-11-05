/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.State;

import BESA.Kernell.Agent.StateBESA;
import BESAFile.Data.Vector3D;
import BESAFile.World.Model.ModelEdifice;
import java.util.Queue;

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
    

    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = radius*2;
        this.sightRange = 4;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors);
        this.speed=2;
        this.consecutiveMSN=new boolean[1000];
        this.nextConsecutive=0;
        this.contMessagesOld=0;
        this.limitContMessagesOld=5;
        
        
    }
    
    public AgentState(int xpos, int ypos,int idfloor, String alias, Vector3D direction, double radius, double height,int width,int length,int nFlooors) {
        this.position=new Position(xpos, ypos, idfloor);
        this.alias = alias;
        this.direction = direction;
        this.radius = radius;
        this.height = height;
        this.sightRange = 2;
        this.type=0;
        this.edifice=new ModelEdifice(width, length, nFlooors);
        this.speed=1;
        this.consecutiveMSN=new boolean[1000];
        this.nextConsecutive=0;
         this.nextConsecutive=0;
        this.contMessagesOld=0;
        this.limitContMessagesOld=5;
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
     
}
