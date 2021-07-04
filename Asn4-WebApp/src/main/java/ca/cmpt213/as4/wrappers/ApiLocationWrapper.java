package ca.cmpt213.as4.wrappers;

import ca.cmpt213.as4.model.CellLocation;

import java.util.ArrayList;
import java.util.List;

public class ApiLocationWrapper {
    public int x;
    public int y;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiLocationWrapper makeFromCellLocation(CellLocation cell) {
        ApiLocationWrapper location = new ApiLocationWrapper();
        location.x = cell.getX();
        location.y = cell.getY();
        return location;
    }
    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static List<ApiLocationWrapper> makeFromCellLocations(Iterable<CellLocation> cells) {
        List<ApiLocationWrapper> locations = new ArrayList<>();
        for (CellLocation cell : cells) {
            locations.add(makeFromCellLocation(cell));
        }
        return locations;
    }
}