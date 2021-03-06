abstract class Question {

    private final String q;
    private final int ans;
    private final int guess;

    Question(String q) {
        this(q,0,0);
    }

    Question(String q, int ans) {
        this(q, ans, 0);
    }
    
    Question(String q, int ans, int g) {
        this.q = q;
        this.ans = ans;
        this.guess = g;
    }

    Question(Question q, int g) {
        this(q.q, q.ans, g);
    }

    int getAns() {
        return this.ans;
    }

    int getGuess() {
        return this.guess;
    }

    abstract Question answer(int gu);

    abstract Markable lock();

    public String toString() {
        return String.format("%s", q);
    }
}
