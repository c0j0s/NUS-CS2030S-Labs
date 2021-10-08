class Question {

    private final String q;
    //private final String[] ops;
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

    Question answer(int gu) {
        return new Question(this.q, this.ans, gu);
    }

    int getGuess() {
        return this.guess;
    }

    int mark() {
        if(guess == ans) {
            return 1;
        }
        else {
            return 0;
        }
    }

    Question lock() {
        return new Question(this, this.g);
    }

    public String toString() {
        return String.format("%s", q);
    }
}
