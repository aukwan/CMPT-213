package ca.cmpt213.as4.wrappers;

import ca.cmpt213.as4.model.Cat;
import ca.cmpt213.as4.model.CellLocation;
import ca.cmpt213.as4.model.Maze;
import ca.cmpt213.as4.model.MazeGame;

import java.util.ArrayList;
import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiBoardWrapper makeFromGame(MazeGame game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();
        wrapper.boardWidth = game.getMazeWidth();
        wrapper.boardHeight = game.getMazeHeight();
        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(game.getPlayerLocation());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(game.getCheeseLocation());
        List<CellLocation> catLocations = new ArrayList<>();
        for (Cat cat : game.getCats()) {
            catLocations.add(cat.getLocation());
        }
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(catLocations);
        wrapper.hasWalls = new boolean[wrapper.boardHeight][wrapper.boardWidth];
        wrapper.isVisible = new boolean[wrapper.boardHeight][wrapper.boardWidth];
        Maze maze = game.getMaze();
        for (int i = 0; i < wrapper.boardHeight; i++) {
            for (int j = 0; j < wrapper.boardWidth; j++) {
                wrapper.hasWalls[i][j] = maze.isCellAWall(new CellLocation(j, i));
                wrapper.isVisible[i][j] = maze.isCellVisible(new CellLocation(j, i));
            }
        }
        return wrapper;
    }
}
