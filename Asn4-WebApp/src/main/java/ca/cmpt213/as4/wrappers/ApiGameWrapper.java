package ca.cmpt213.as4.wrappers;

import ca.cmpt213.as4.model.MazeGame;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiGameWrapper makeFromGame(MazeGame game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;
        wrapper.numCheeseFound = game.getNumberCheeseCollected();
        wrapper.numCheeseGoal = game.getNumberCheeseToCollect();
        wrapper.isGameLost = game.hasUserLost();
        wrapper.isGameWon = game.hasUserWon();
        return wrapper;
    }
}
