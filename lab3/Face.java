class Face implements Cloneable {
    private final int[][] grid;
    private final int[][] gridVertical;

    private static final int SIZE = 3;

    Face(int[][] grid) {
        this.grid = grid;
        this.gridVertical = genVerticalIntArray(grid);
    }

    Face right() {
        Face f = this.clone();
        int[][] tmp = f.grid;
        for (int i = 0; i < grid.length; i++) {
            int[] row = reverseArray(f.gridVertical[i]);
            tmp[i] = row;
        }
        return f;
    }

    Face left() {
        Face f = this.clone();
        int[][] tmp = f.grid;
        for (int i = 0; i < grid.length; i++) {
            int[] row = f.gridVertical[i];
            tmp[SIZE - 1 - i] = row;
        }
        return f;
    }

    Face half() {
        return this.right().right();
    }

    int[][] toIntArray() {
        return this.grid;
    }

    /*
     * =============== Helper function ===============
     */
    static int[] reverseArray(int[] row) {
        int[] tmp = new int[row.length];
        for (int i = 0; i < row.length; i++) {
            tmp[i] = row[row.length - 1 - i];
        }
        return tmp;
    }

    int[][] toVerticalIntArray() {
        return this.gridVertical;
    }

    Face setVerIntArray(int row, int[] values) {
        Face f = this.clone();
        int[][] tmp = f.grid;
        for (int i = 0; i < values.length; i++) {
            tmp[i][row] = values[i];
        }
        return f;
    }

    static int[][] genVerticalIntArray(int[][] grid) {
        int[][] tmp = new int[SIZE][SIZE];
        for (int i = 0; i < grid.length; i++) {
            int[] row = new int[SIZE];
            for (int j = 0; j < SIZE; j++) {
                row[j] = grid[j][i];
            }
            tmp[i] = row;
        }
        return tmp;
    }

    /*
     * ================ Override Methods ================
     */
    @Override
    public Face clone() {
        int[][] tmp = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                tmp[i][j] = grid[i][j];
            }
        }
        return new Face(tmp);
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < SIZE; i++) {
            output += "\n";
            for (int j = 0; j < SIZE; j++) {
                output += String.format("%02d", grid[i][j]);
            }
        }
        return output + "\n";
    }
}
