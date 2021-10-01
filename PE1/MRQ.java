class MRQ extends MCQ {
    private final int[] ans;
    private final int[] guess;

    MRQ(String q, String[] ops, int[] ans) {
        super(q, ops, 0);
        this.ans = sort(ans);
        this.guess = new int[0];
        
    }

    MRQ(MRQ m, int[] ng) {
        super(m,0);
        this.ans = m.ans;
        this.guess = ng;
    }
    
    MRQ answer(int g) {
        int size = guess.length;

        for (int i = 0; i < guess.length; i++) {
            if(g == guess[i]) {
                guess[i] = 0;
                size--;
                break;
            }
        }
        boolean add = false;
        if(size == guess.length) {
            size++;
            add = true;
        }
        int c = 0;
        int[] ng = new int[size];
        for(int i = 0; i < guess.length; i++) {
            if(guess[i] != 0) {
                ng[c] = guess[i];
                c++;
            }
        }

        if(add) {
            ng[ng.length - 1] = g;
        }
        ng = sort(ng);
        return new MRQ(this, ng);
    }

    public int mark() {
        if(guess.length != ans.length) {
            return 0;
        }
        for(int i = 0; i < guess.length; i++) {
            if(guess[i] != ans[i]) {
                return 0;
            }
        }
        return 1;
    }

    public Markable lock() {
        return this;
    }

    public String toString() {
        String g = "";
        for(int i = 0; i < guess.length; i++){
            g +=  guess[i] + " ";
        }
        return String.format("%s Your answer: [ %s]", super.getQues(), g);
    }

    /**
     * dysfunctional sort method, only work with arrays without 0.
     * @param arr
     * @return
     */
    static int[] sort(int[] arr) {
        int[] na = new int[arr.length];
        na[0] = arr[0];
        int c = 0;
        for (int i = 1; i < arr.length; i++) {
            do {
                if (c == (arr.length - 1)) {
                    na[c] = arr[i];
                    break;
                }
                if(na[c] > arr[i] || na[c] == 0) {
                    for(int j = na.length - 1; j > c; j--){
                        na[j] = na[j - 1];
                    }
                    na[c] = arr[i];
                    c = 0;
                }else{
                    c++;
                }
            }while(c != 0);
        }
        return na;
    }
}
