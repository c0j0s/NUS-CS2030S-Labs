class TFQ extends MCQ {


    TFQ(String q, String ans) {
        super(q, new String[] {"True","False"}, TFQ.conAns(ans));
    }

    TFQ(TFQ t, int ans) {
        super(t, ans);
    }

    TFQ answer(String ans) {
        return new TFQ(this, TFQ.conAns(ans));
    }

    TFQ answer(int ans) {
        return new TFQ(this, ans);
    }

    Markable lock() {
        return this;
    }

    static int conAns(String an){
        int a = 1;
        if(an.equals("False")) {
            a = 2;
        }
        return a;
    }

    public String toString() {
        return super.toString();
    }
}
