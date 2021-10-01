class FillInBlank extends Question implements Markable {

    private final Grader gr;

    FillInBlank(String q, int ans) {
        super(q, ans);
        gr = new Grader(ans);
    }

    FillInBlank(String q, Grader gr) {
        super(q);
        this.gr = gr;
    }

    FillInBlank(FillInBlank f, int g) {
        super(f, g);
        gr = f.gr;
    }

    FillInBlank answer(int g) {
        return new FillInBlank(this, g);
    }

    public int mark() {
        return gr.compute(super.getGuess());
    }

    public Markable lock() {
        return this;
    }

    public String toString() {
        return String.format("%s; Your answer: %d",super.toString(),super.getGuess());
    }
}
