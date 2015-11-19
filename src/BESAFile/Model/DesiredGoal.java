/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Model;

import BESAFile.Agent.State.Position;
import BESAFile.World.Model.ModelFloor;


/**
 *
 * @author berme_000
 */
public class DesiredGoal {
    
    
    protected Position goal;
    protected ModelFloor gridWeights;
    protected boolean attraction;

    public DesiredGoal(Position goal, ModelFloor gridWeights, boolean attraction) {
        this.goal = goal;
        this.gridWeights = gridWeights;
        this.attraction = attraction;
    }

    

    public Position getGoal() {
        return goal;
    }

    public void setGoal(Position goal) {
        this.goal = goal;
    }

    public ModelFloor getGridWeights() {
        return gridWeights;
    }

    public void setGridWeights(ModelFloor gridWeights) {
        this.gridWeights = gridWeights;
    }

    public boolean isAttraction() {
        return attraction;
    }

    public void setAttraction(boolean attraction) {
        this.attraction = attraction;
    }

    @Override
    public String toString() {
        return "DesiredGoal{" + "goal=" + goal + ", gridWeights=" + gridWeights + ", attraction=" + attraction + '}';
    }
    
    
    
    
    
}
