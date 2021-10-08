class MRQ extends MCQ {
    private final int[] ans;
    private final int[] guess;

    MRQ(String q, String[] ops, int[] ans) {
        super(q, ops, 0);
        this.ans = ans;
        this.guess = new int[ops.length];
    }

    MRQ(MRQ m, int[] ng) {
        super(m,0);
        this.ans = m.ans;
        this.guess = ng;
    }
    
    MRQ answer(int g) {
        int[] ng = new int[guess.length];
        for (int i = 0; i < ng.length; i++) {
            ng[i] = guess[i];
        }
        int i = g - 1;
        if (i < guess.length) {
            if(guess[i] == 1){
                ng[i] = 0;
            }else{
                ng[i] = 1;
            }
        }
        return new MRQ(this, ng);
    }

    public int mark() {
        int correct = 0;
        int count = 0;
        for(int i = 0; i < guess.length; i++) {
            if (guess[i] != 0) {
                for (int j = 0; j < ans.length; j++) {
                    if((i + 1) == ans[j]) {
                        correct++;
                    }
                }
                count++;
            }
        }
        return count == ans.length? correct == ans.length? 1:0 : 0;
    }

    Markable lock() {
        return this;
    }

    public String toString() {
        String g = "";
        for(int i = 0; i < guess.length; i++){
            if (guess[i] != 0) { 
                g +=  (i + 1) + " ";
            }
        }
        return String.format("%s Your answer: [ %s]", getQues(), g);
    }

}
