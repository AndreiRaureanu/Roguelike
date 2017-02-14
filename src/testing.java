/**
 * Created by User on 11-Feb-17.
 */
public class testing {

    public static String[][] map = new String[20][25];

    public static void main (String[] args) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 25; j++) {
                map[i][j] = ",";
            }
        }
        for(int j = 0; j < 25; j++) {
            map[0][j] = "#";
        }
        for(int i = 0; i < 20; i++) {
            map[i][0] = "#";
        }
        for(int i = 19; i >= 0; i--) {
            map[i][24] = "#";
        }
        for(int i = 0; i < 25; i++) {
            map[19][i] = "#";
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 25; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
