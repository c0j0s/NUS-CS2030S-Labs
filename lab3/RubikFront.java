class RubikFront extends Rubik {
    private final int[][][] grid;

    private static final int FACES = 6;
    private static final int SIZE = 3;

    // Face index
    private static final int U = 0;
    private static final int L = 1;
    private static final int F = 2;
    private static final int R = 3;
    private static final int D = 4;
    private static final int B = 5;

    RubikFront(int[][][] grid) {
        super(grid);
        this.grid = grid;
    }

    RubikFront(Rubik r) {
        this(r.getGrid());
    }

    @Override
    RubikFront right() {
        RubikFront r = this.clone();
        int[][][] tmp = r.getGrid();

        // update face 2
        tmp[F] = new Face(grid[F]).right().toIntArray();

        // turn face 0, 1, 4, 5
        // --------------------

        // update face 0 row 3 with reverse of face 1 column 3
        tmp[U][2] = Face.reverseArray(new Face(grid[L]).toVerticalIntArray()[2]);

        // update face 1 column 3 with face 4 row 0
        tmp[L] = new Face(grid[L]).setVerIntArray(2, grid[D][0]).toIntArray();

        // update face 3 column 1 with face 0 row 3
        tmp[R] = new Face(grid[R]).setVerIntArray(0, grid[U][2]).toIntArray();

        // update face 4 row 1 with reverse of face 3 column 1
        tmp[D][0] = Face.reverseArray(new Face(grid[R]).toVerticalIntArray()[0]);

        return r;
    }

    @Override
    RubikFront left() {
        RubikFront r = this.clone();
        int[][][] tmp = r.getGrid();

        // update face 2
        tmp[F] = new Face(grid[2]).left().toIntArray();

        // turn face 0, 1, 4, 5
        // --------------------

        // update face 0 row 3 with face 3 column 1
        tmp[U][2] = new Face(grid[R]).toVerticalIntArray()[0];

        // update face 1 column 3 with reverse of face 0 row 3
        tmp[L] = new Face(grid[L]).setVerIntArray(2, Face.reverseArray(grid[U][2])).toIntArray();

        // update face 3 column 1 with reverse of face 4 row 1
        tmp[R] = new Face(grid[R]).setVerIntArray(0, Face.reverseArray(grid[D][0])).toIntArray();

        // update face 4 row 1 with face 1 column 3
        tmp[D][0] = new Face(grid[L]).toVerticalIntArray()[2];

        return r;
    }

    @Override
    RubikFront half() {
        return this.right().right();
    }

    RubikFront rightView() {
        int[][][] tmp = new int[FACES][SIZE][SIZE];

        // offset and flip faces to new position
        tmp[L] = new Face(grid[F]).toIntArray();
        tmp[F] = new Face(grid[R]).toIntArray();

        tmp[U] = new Face(grid[U]).right().toIntArray();
        tmp[D] = new Face(grid[D]).left().toIntArray();

        tmp[R] = new Face(grid[B]).half().toIntArray();
        tmp[B] = new Face(grid[L]).half().toIntArray();

        return new RubikFront(tmp);
    }

    RubikFront leftView() {
        int[][][] tmp = new int[FACES][SIZE][SIZE];

        // offset and flip faces to new position
        tmp[F] = new Face(grid[L]).toIntArray();
        tmp[R] = new Face(grid[F]).toIntArray();

        tmp[U] = new Face(grid[U]).left().toIntArray();
        tmp[D] = new Face(grid[D]).right().toIntArray();

        tmp[L] = new Face(grid[B]).half().toIntArray();
        tmp[B] = new Face(grid[R]).half().toIntArray();

        return new RubikFront(tmp);
    }

    RubikFront upView() {
        int[][][] tmp = new int[FACES][SIZE][SIZE];

        // offset faces to new position
        tmp[U] = new Face(grid[B]).toIntArray();
        tmp[F] = new Face(grid[U]).toIntArray();
        tmp[D] = new Face(grid[F]).toIntArray();
        tmp[B] = new Face(grid[D]).toIntArray();

        // rotate side faces
        tmp[L] = new Face(grid[L]).right().toIntArray();
        tmp[R] = new Face(grid[R]).left().toIntArray();

        return new RubikFront(tmp);
    }

    RubikFront downView() {
        int[][][] tmp = new int[FACES][SIZE][SIZE];

        // offset faces to new position
        tmp[U] = new Face(grid[F]).toIntArray();
        tmp[F] = new Face(grid[D]).toIntArray();
        tmp[D] = new Face(grid[B]).toIntArray();
        tmp[B] = new Face(grid[U]).toIntArray();

        // rotate side faces
        tmp[L] = new Face(grid[L]).left().toIntArray();
        tmp[R] = new Face(grid[R]).right().toIntArray();

        return new RubikFront(tmp);
    }

    RubikFront backView() {
        return this.leftView().leftView();
    }

    RubikFront frontView() {
        return this;
    }

    @Override
    public RubikFront clone() {
        // deep copy clone
        int[][][] tmp = new int[FACES][SIZE][SIZE];

        for (int i = 0; i < grid.length; i++) {
            tmp[i] = new Face(grid[i]).clone().toIntArray();
        }

        return new RubikFront(tmp);
    }

    @Override
    public String toString() {
        String output = "";
        String[] tmp = { "", "", "" };

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < SIZE; j++) {
                String row = "";

                for (int k = 0; k < SIZE; k++) {
                    row += String.format("%02d", grid[i][j][k]);
                }

                // join face 1 and 2
                if (i == L || i == F) {
                    tmp[j] += row;
                    continue;
                }

                if (i == SIZE) {
                    // output joined rows
                    output += "\n" + tmp[j] + row;
                } else {
                    // output normal rows
                    output += "\n......" + row + "......";
                }
            }
        }
        return output + "\n";
    }
}
