package model;

public class EasyComputer extends Computer {

    /**
     * rewrites standered execute action for easy computer
     */
    @Override
    public void executeAction(Game game) {
        /**
         * custom easy logic for computer
         */
        super.executeAction(game);
    }
}
