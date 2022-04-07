//-----------------------------------------------------------
//File:   HardComputer.java
//Desc:   This program creates a hard computer object
//-----------------------------------------------------------
package model;

public class HardComputer extends Computer{

    /**
     * rewrites standered execute action for hard computer
     */
    @Override
    public void executeAction(Game game) {
        /**
         * custom hard logic for computer
         */
        super.executeAction(game);
    }
}
