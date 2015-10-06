/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Agent.kidnaped.State;

import BESAFile.Agent.State.AgentState;
import BESAFile.World.Model.ModelEdifice;

/**
 *
 * @author angel
 */
public class KidnapedState extends  AgentState{

    public KidnapedState(ModelEdifice edifice, int xpos, int ypos, int idfloor, String alias) {
        super(edifice, xpos, ypos, idfloor, alias);
    }

    
    
}
