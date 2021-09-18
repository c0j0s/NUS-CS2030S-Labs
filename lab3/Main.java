import java.util.Scanner;

class Main {
    static final int NFACES = 6;
    static final int NROWS = 3;
    static final int NCOLS = 3;

    static Rubik oneMove(Rubik rubik, String move) {
        // System.out.print("Face " + move.charAt(0));

        char face = move.charAt(0);
        switch (face) {
            case 'L':
                rubik = new RubikLeft(rubik);
                break;
            case 'R':
                rubik = new RubikRight(rubik);
                break;
            case 'U':
                rubik = new RubikUp(rubik);
                break;
            case 'D':
                rubik = new RubikDown(rubik);
                break;
            case 'B':
                rubik = new RubikBack(rubik);
                break;
            default:
                rubik = new RubikFront(rubik);
                break;
        }
        // System.out.println("View Changed");
        // System.out.println(rubik.frontView());

        if (move.length() == 1) {                
            // System.out.println(" right turn");
            rubik = rubik.right();
        } else {
            if (move.charAt(1) == '\'') {
                // System.out.println(" left turn");
                rubik = rubik.left();
            } else {
                // System.out.println(" half turn");
                rubik = rubik.half();
            }
        }

        // System.out.println(rubik);

        return rubik;
    }


    public static void main(String[] args) {
        int[][][] grid = new int[NFACES][NROWS][NCOLS];

        Scanner sc = new Scanner(System.in);
        for (int k = 0; k < NFACES; k++) {
            for (int i = 0; i < NROWS; i++) {
                for (int j = 0; j < NCOLS; j++) {
                    grid[k][i][j] = sc.nextInt();
                }
            }
        }
        Rubik rubik = new RubikFront(grid);

        while (sc.hasNext()) {
            rubik = oneMove(rubik, sc.next());
        }
        System.out.println(rubik);
    }
}
