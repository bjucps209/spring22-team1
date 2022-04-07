//-----------------------------------------------------------
//File:   MediumComputer.java
//Desc:   This program creates a medium computer object
//-----------------------------------------------------------
package model;

public class MediumComputer extends Computer{
    
    /**
     * rewrites standered execute action for medium computer
     */
    @Override
    public void executeAction(Game game) {
        /**
         * custom medium logic for computer
         */
        super.executeAction(game);
    }
}
