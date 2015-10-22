/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

import BESA.Kernell.Agent.Event.DataBESA;
import BESAFile.Agent.State.Motion;
import BESAFile.Agent.State.Position;
import BESAFile.Model.SeenObject;
import BESAFile.Model.SeenWall;
import java.util.List;

/**
 *
 * @author berme_000
 */
public class ActionDataAgent extends DataBESA {
    
    private float sightRange;
    protected double radius;
    protected double height;
    private String action;
    private String alias;
    private int type;
    protected  int idfloor;
    private List<SeenObject> seenObjects;
    private Vector3D viewDirection;
    private Position position;
    private Motion motion;
    private float speed;
    

    public ActionDataAgent(int type,String alias) {
        this.alias = alias;
        this.type = type;
    }

    public ActionDataAgent(int type,String alias,String action) {
        this.alias = alias;
        this.type = type;
        this.action=action;
    }

    public ActionDataAgent(int type,String alias,String action,Vector3D viewDirection) {
        this.alias = alias;
        this.type = type;
        this.action=action;
        this.viewDirection=viewDirection;
    }
    public ActionDataAgent(int type,String alias,Motion motion,Position position,float speed,String action) {
        this.alias = alias;
        this.type = type;
        this.action=action;
        this.motion=motion;
        this.position=position;
        this.speed=speed;
    }
    
    
     public ActionDataAgent(int type,String alias,String action,int xpos, int ypos, int idfloor,float speed) {
        this.alias = alias;
        this.type = type;
        this.action=action;
    }
    
    public ActionDataAgent(int type,float sightRange, double radius, double height, String alias,Position position, String action) {
        this.idfloor=position.getIdfloor();
        this.sightRange = sightRange;
        this.radius = radius;
        this.height = height;
        this.action = action;
        this.alias = alias;
        this.type = type;
        this.position=position;
    }
    
    public ActionDataAgent(String action) {
        this.action = action;
    }

    //Response 
    public ActionDataAgent(String action,String alias,Position position) {
        this.action = action;
        this.alias=alias;
        this.position=position;
    }
    
    public ActionDataAgent(String alias, int type, List<SeenObject> seenObjects,Position position,String action) {
        this.alias = alias;
        this.type = type;
        this.seenObjects = seenObjects;
        this.action=action;
        this.position=position;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getSightRange() {
        return sightRange;
    }

    public void setSightRange(float sightRange) {
        this.sightRange = sightRange;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdfloor() {
        return idfloor;
    }

    public void setIdfloor(int idfloor) {
        this.idfloor = idfloor;
    }

    public List<SeenObject> getSeenObjects() {
        return seenObjects;
    }

    public void setSeenObjects(List<SeenObject> seenObjects) {
        this.seenObjects = seenObjects;
    }


    public Vector3D getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(Vector3D viewDirection) {
        this.viewDirection = viewDirection;
    }

    public Motion getMotion() {
        return motion;
    }

    public void setMotion(Motion motion) {
        this.motion = motion;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
