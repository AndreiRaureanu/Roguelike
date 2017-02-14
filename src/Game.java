import java.util.*;
import java.io.IOException;

/**
 * Created by User on 11-Feb-17.
 */
public class Game {
    Scanner input;
    public DungeonGenerator zone;

    public static void main (String[] args) {
        String dir;
        Scanner input = new Scanner(System.in);
        DungeonGenerator zone = new DungeonGenerator(40,80,350,7000);
        Player player = new Player(zone.getFloorSet().get((int) (Math.random() * zone.getFloorSet().size())).x,
                zone.getFloorSet().get((int) (Math.random() * zone.getFloorSet().size())).y);
        do {
            zone.drawDungeon(player);
            System.out.println("What is your direction");
            while (!input.hasNext()) {
                input.next();
            }
            dir = input.next();
            if(dir.equals("w")) {
                if(zone.dungeon[player.getX() - 1][player.getY()] != zone.WALL) {
                    player.setX(player.getX() - 1);
                }
            }
            if(dir.equals("s")) {
                if(zone.dungeon[player.getX() + 1][player.getY()] != zone.WALL) {
                    player.setX(player.getX() + 1);
                }
            }
            if(dir.equals("a")) {
                if(zone.dungeon[player.getX()][player.getY() - 1] != zone.WALL) {
                    player.setY(player.getY() - 1);
                }
            }
            if(dir.equals("d")) {
                if(zone.dungeon[player.getX()][player.getY() + 1] != zone.WALL) {
                    player.setY(player.getY() + 1);
                }
            }
            System.out.print("\033[H\033[2J");
        } while(!input.equals(""));
    }
}
