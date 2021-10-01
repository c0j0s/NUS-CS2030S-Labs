class MRQ extends Question {
    private final String[] ops;
    private final String[] ans;
    private final String[] guess;

    MRQ(String q, String[] ops, int[] ans, int[] g) {
        super(q);
        this.ops = ops;
        this.ans = ans;
        this.guess = g;
    }

    MRQ(MRQ m, int ng) {
        this(m.q, m.ops, m.ans, ng);
    }
    
    MRQ answer(int g) {
                
        int size = guess.length;
        for (int i = 0; i < guess.length; i++) {
            if(g == guess[i]) {
                guess[i] = 0;
                size--;
            }
        }
        int c = 0;
        int[] ng = new int[size];
        for(int i = 0; i < guess.length; i++) {
            if(guess[i] != 0) {
                ng[c] = guess[i];
                c++;
            }
        }

        return new MRQ(this, ng);
    }

    
}
