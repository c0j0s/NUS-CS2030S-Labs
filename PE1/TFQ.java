class TFQ extends MCQ {
    

    TFQ(String q, String ans) {
        super(q, new String[] {"True","False"}, TFQ.conAns(ans));
    }

    TFQ(TFQ t, String ans) {
        super(t, TFQ.conAns(ans));
    }

    TFQ answer(String ans) {
        return new TFQ(this, ans);
    }

    static int conAns(String an){
        int a = 0;
        if(an.equals("False")) {
            a = 1;
        }
        return a;
    }

    public String toString() {
        return super.toString();
    }
}
