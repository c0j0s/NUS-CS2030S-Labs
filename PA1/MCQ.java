class MCQ extends Question implements Markable{
    
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

    public int mark() {
        return getAns() == getGuess()? 1:0;
    }

    Markable lock() {
        return this;
    }

    String[] getOps() {
        return ops;
    }

    String opsToString() {
        String op = "";
         
        for (int i = 0; i < ops.length; i++) {
            op += "[" + (i + 1) + ":" + ops[i] + "]";
        }
        return op;
    }

    String getQues(){
        return String.format("%s %s;", super.toString(), opsToString());
    }

    @Override
    public String toString() {
       int g = getGuess();
        String a = "[ ? ]";
        if(g != 0) {
            a = "[ " + g + ":" + ops[g - 1] + " ]";
        }
        return String.format("%s Your answer: %s", getQues(), a);
    }
}
