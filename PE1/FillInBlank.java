class FillInBlank extends Question {

    FillInBlank(String q, int ans) {
        super(q, ans);
    }

    FillInBlank(FillInBlank f, int g) {
        super(f, g);
    }

    FillInBlank answer(int g) {
        return new FillInBlank(this, g);
    }

    public String toString() {
        return String.format("%s; Your answer: %d",super.toString(),super.getGuess());
    }
}
