class Grader {
    private final int ans;

    Grader(int ans) {
        this.ans  = ans;
    }

    int getAns(){
        return ans;
    }

    int compute(int g) {
        return ans == g? 1:0;
    }
}
