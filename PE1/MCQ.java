class MCQ extends Question {
    
    private final String[] ops;

    MCQ(String q, String[] ops, int ans){
        super(q, ans);
        this.ops = ops;
    }

    MCQ(MCQ m, int g) {
        super(m,g);
        this.ops = m.ops;
    }

    MCQ answer(int g){
        return new MCQ(this, g);
    }
    
    @Override
    public String toString() {
        String op = "";
         
        for (int i = 0; i < ops.length; i++) {
            op += "[" + (i + 1) + ":" + ops[i] + "]";
        }
        int g = getGuess();
        String a = "[ ? ]";
        if(g != 0) {
            a = "[ " + g + ":" + ops[g - 1] + " ]";
        }
        //System.out.println(op);
        return String.format("%s %s; Your answer: %s", super.toString(), op, a);
    }
}
