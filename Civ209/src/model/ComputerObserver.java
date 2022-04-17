package model;

import java.util.ArrayList;

public interface ComputerObserver {
    public void sendTroopsFromCity(City selectedCity, Coordinate destination);

    public void sendTroopsFromGround(ArrayList<Troop> selectedTroops, Coordinate destination);
}
