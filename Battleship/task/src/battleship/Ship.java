package battleship;

public class Ship {
    private final String name;
    private final int size;
    private String startCoordinate;
    private String endCoordinate;
    private int hits = 0;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(String startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public String getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(String endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    // New method to record a hit on the ship
    public void recordHit() {
        this.hits++;
    }

    // New method to check if the ship has been sunk
    public boolean isSunk() {
        return hits == size;
    }
}
