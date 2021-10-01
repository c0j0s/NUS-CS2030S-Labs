abstract class Grader {
    private final int ans;

    Grader(int ans) {
        this.ans  = ans;
    }

    int getAns(){
        return ans;
    }

    abstract int compute(int g);
}
