abstract class Rubik implements Cloneable {
    private final int[][][] grid;

    Rubik(int[][][] grid) {
        this.grid = grid;
    }

    abstract Rubik right();

    abstract Rubik left();

    abstract Rubik half();

    abstract Rubik rightView();

    abstract Rubik leftView();

    abstract Rubik upView();

    abstract Rubik downView();

    abstract Rubik backView();

    abstract Rubik frontView();

    int[][][] getGrid() {
        return this.grid;
    }

    @Override
    public abstract Rubik clone();
}
