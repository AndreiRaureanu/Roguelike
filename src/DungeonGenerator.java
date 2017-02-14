import java.util.*;
import java.util.stream.*;
/**
 * Created by User on 11-Feb-17.
 */
public class DungeonGenerator {
    private List<Tile> wallSet;
    private List<Tile> floorSet;
    private List<Tile> boundarySet;
    private Tile[][] board;
    private int rows;
    private int columns;
    private int hallSize;
    private int lengthOfHall;
    public final String GRASS = ",";
    public final String WALL = "#";
    public String[][] dungeon;
    public Set<Tile> finalWall;
    public Set<Tile> finalFloor;

    public DungeonGenerator(int rows, int columns, int lengthOfHall, int hallSize) {
        this.rows = rows;
        this.columns = columns;
        this.lengthOfHall = lengthOfHall;
        this.hallSize = hallSize;
        this.dungeon = new String[getRows()][getColumns()];
        generateMap();
        generateHallways(getLengthOfHall(), getHallSize());
        refineDungeon();
        fillDungeonBorders();
    }

    public void generateMap() {
        setBoard(getRows(), getColumns());
        for(int i = 0; i < getRows(); i++)
        {
            for (int j = 0; j < getColumns(); j++)
            {
                getBoard()[i][j] = new Tile(i, j);
            }
        }
        setWallSet(new ArrayList<Tile>());
        for(int i = 0; i < getRows(); i++)
        {
            for(int j = 0; j < getColumns(); j++)
            {
                getWallSet().add(getBoard()[i][j]);
            }
        }
        setFloorSet(new ArrayList<Tile>());
    }

    public Tile pickRandomNeighbour(Tile activeTile) {
        List<Tile> tempTiles = new ArrayList<Tile>();
        Tile returnTile = null;
        if(activeTile.x + 1 < getRows() && !getBoard()[activeTile.x +1][activeTile.y].open) {
            tempTiles.add(getBoard()[activeTile.x + 1][activeTile.y]);
        }
        if (activeTile.x - 1 >= 0 && !getBoard()[activeTile.x -1][activeTile.y].open) {
            tempTiles.add(getBoard()[activeTile.x - 1][activeTile.y]);
        }
        if(activeTile.y + 1 < getColumns() && !getBoard()[activeTile.x][activeTile.y + 1].open) {
            tempTiles.add(getBoard()[activeTile.x][activeTile.y + 1]);
        }
        if(activeTile.y - 1 >= 0 && !getBoard()[activeTile.x][activeTile.y - 1].open) {
            tempTiles.add(getBoard()[activeTile.x][activeTile.y - 1]);
        }
            if(tempTiles.size() == 0) {
                returnTile = new Tile(getRows()/2, getColumns()/2);
            } else {
                int rand = (int) (Math.random() * tempTiles.size());
                returnTile = tempTiles.get(rand);
            }
        return returnTile;
    }

    public Tile pickRandomNewHallway() {
        List<Tile> tempTiles = new ArrayList<Tile>();
        Tile returnTile = null;
        for (Tile tile : getFloorSet()) {
            if (tile.x + 1 < getRows() && !getBoard()[tile.x + 1][tile.y].open) {
                tempTiles.add(getBoard()[tile.x + 1][tile.y]);
            }
            if (tile.x - 1 >= 0 && !getBoard()[tile.x - 1][tile.y].open) {
                tempTiles.add(getBoard()[tile.x - 1][tile.y]);
            }
            if (tile.y + 1 < getColumns() && !getBoard()[tile.x][tile.y + 1].open) {
                tempTiles.add(getBoard()[tile.x][tile.y + 1]);
            }
            if (tile.y - 1 >= 0 && !getBoard()[tile.x][tile.y - 1].open) {
                tempTiles.add(getBoard()[tile.x][tile.y - 1]);
            }
        }
            if (tempTiles.size() == 0) {
                returnTile = tempTiles.get(0);
            } else {
                int rand = (int) (Math.random() * tempTiles.size());
                returnTile = tempTiles.get(rand);
            }
        return returnTile;
    }

    public void generateHallways(int hallLength, int fillSize) {
        int rand = (int) (Math.random() * getWallSet().size());
        Tile activeTile = getWallSet().get(rand);
        Tile placeHolder = activeTile;

        getFloorSet().add(activeTile);
        getWallSet().remove(activeTile);
        activeTile.open = true;

        while(getFloorSet().size() < fillSize) {
            for(int i = 0; i < hallLength - 1; i++) {
                placeHolder = pickRandomNeighbour(activeTile);
                if(placeHolder == null) {
                    break;
                } else {
                    activeTile = placeHolder;
                    getFloorSet().add(activeTile);
                    getWallSet().remove(activeTile);
                    activeTile.open = true;
                }
            }
            activeTile = pickRandomNewHallway();
            if(activeTile == null) {
                break;
            } else {
                getFloorSet().add(activeTile);
                getWallSet().remove(activeTile);
                activeTile.open = true;
            }
        }
    }

    public void fillDungeonBorders() {
        for(int i = 0; i < getColumns(); i++) {
            getWallSet().add(getBoard()[0][i]);
            getFloorSet().remove(getBoard()[0][i]);
        }
        for(int i = 0; i < getRows(); i++) {
            getWallSet().add(getBoard()[i][0]);
            getFloorSet().remove(getBoard()[i][0]);
        }
        for(int i = getRows() - 1; i >= 0; i--) {
            getWallSet().add(getBoard()[i][getColumns() - 1]);
            getFloorSet().remove(getBoard()[i][getColumns() - 1]);
        }
        for(int i = 0; i < getColumns(); i++) {
            getWallSet().add(getBoard()[getRows() - 1][i]);
            getFloorSet().remove(getBoard()[getRows() - 1][i]);
        }
    }

    public void refineDungeon() {
        getWallSet().stream().distinct().collect(Collectors.toList());
        getFloorSet().stream().distinct().collect(Collectors.toList());
        Tile[][] refinedBoard = new Tile[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                refinedBoard[i][j] = new Tile(i, j);
            }
        }
        for (Tile tile : getWallSet()) {
            refinedBoard[tile.x][tile.y].open = false;
        }
        for (Tile tile : getFloorSet()) {
            refinedBoard[tile.x][tile.y].open = true;
        }
        for (int i = 2; i < getRows() - 2; i++) {
            for (int j = 2; j < getColumns() - 2; j++) {
                if (refinedBoard[i - 1][j].open && refinedBoard[i + 1][j].open && refinedBoard[i][j - 1].open && refinedBoard[i][j + 1].open) {
                    getFloorSet().add(refinedBoard[i][j]);
                    getWallSet().remove(refinedBoard[i][j]);
                }
                if (!refinedBoard[i][j + 1].open && refinedBoard[i][j + 2].open) {
                    getFloorSet().add(refinedBoard[i][j]);
                    getFloorSet().add(refinedBoard[i][j + 1]);
                    getWallSet().remove(refinedBoard[i][j]);
                    getWallSet().remove(refinedBoard[i][j + 1]);
                }
                if (!refinedBoard[i][j - 1].open && refinedBoard[i][j - 2].open) {
                    getFloorSet().add(refinedBoard[i][j]);
                    getFloorSet().add(refinedBoard[i][j - 1]);
                    getWallSet().remove(refinedBoard[i][j]);
                    getWallSet().remove(refinedBoard[i][j - 1]);
                }
                if ((!refinedBoard[i + 1][j].open && refinedBoard[i + 2][j].open)) {
                    getFloorSet().add(refinedBoard[i][j]);
                    getFloorSet().add(refinedBoard[i + 1][j]);
                    getWallSet().remove(refinedBoard[i][j]);
                    getWallSet().remove(refinedBoard[i + 1][j]);
                }
                if (!refinedBoard[i - 1][j].open && refinedBoard[i - 2][j].open) {
                    getFloorSet().add(refinedBoard[i][j]);
                    getFloorSet().add(refinedBoard[i - 1][j]);
                    getWallSet().remove(refinedBoard[i][j]);
                    getWallSet().remove(refinedBoard[i - 1][j]);
                }
            }
        }
    }

    public void drawDungeon(Player player) {
        getWallSet().stream().distinct().collect(Collectors.toList());
        getFloorSet().stream().distinct().collect(Collectors.toList());
        dungeon = new String[getRows()][getColumns()];
        for(Tile tile: getWallSet()) {
            dungeon[tile.x][tile.y] = WALL;
        }
        for(Tile tile: getFloorSet()) {
            dungeon[tile.x][tile.y] = GRASS;
        }
        dungeon[player.getX()][player.getY()] = player.MODEL;
        for(int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                System.out.print(dungeon[i][j]);
            }
            System.out.println();
        }
    }

    public List<Tile> getWallSet() {
        return wallSet;
    }

    public void setWallSet(List<Tile> wallSet) {
        this.wallSet = wallSet;
    }

    public List<Tile> getFloorSet() {
        return floorSet;
    }

    public void setFloorSet(List<Tile> floorSet) {
        this.floorSet = floorSet;
    }

    public List<Tile> getBoundarySet() {
        return boundarySet;
    }

    public void setBoundarySet(List<Tile> boundarySet) {
        this.boundarySet = boundarySet;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getHallSize() {
        return hallSize;
    }

    public void setHallSize(int hallSize) {
        this.hallSize = hallSize;
    }

    public int getLengthOfHall() {
        return lengthOfHall;
    }

    public void setLengthOfHall(int lengthOfHall) {
        this.lengthOfHall = lengthOfHall;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(int rows, int columns) {
        this.board = new Tile[rows][columns];

    }
}