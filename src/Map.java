/**
 * Created by User on 11-Feb-17.
 */
public class Map {
    private int height;
    private int length;
    private String[][] map;

    public Map(int length, int height) {
        setLength(length);
        setHeight(height);
        setMap(getLength(), getHeight());
        fillMap();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String[][] getMap() {
        return map;
    }

    public void setMap(int length, int height) {
        this.map = new String[length][height];
    }

    public void fillMap() {
        //fills everything with grass
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                map[i][j] = ",";
            }
        }
        //fills top row with walls
        for(int i = 0; i < getHeight(); i++) {
            map[0][i] = "#";
        }
        //fills leftmost column with walls
        for(int i = 0; i < getLength(); i++) {
            map[i][0] = "#";
        }
        //fills rightmost column with walls
        for(int i = getLength() - 1; i >= 0; i--) {
            map[i][getHeight() - 1] = "#";
        }
        //fills bottom row with walls
        for(int i = 0; i < getHeight(); i++) {
            map[getLength() - 1][i] = "#";
        }
    }

    public void drawMap() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
