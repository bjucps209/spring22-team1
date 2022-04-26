//-----------------------------------------------------------
//File:   ComputerObserver.java
//Desc:   File holds the observer to allow computer to render
//troops on screen.
//-----------------------------------------------------------

package model;

import java.util.ArrayList;

public interface ComputerObserver {
    public void renderTroops(ArrayList<Troop> troops);
}
