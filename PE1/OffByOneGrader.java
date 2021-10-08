class OffByOneGrader extends Grader {

    OffByOneGrader(int ans) {
        super(ans);
    }

    int compute(int g) {
        if(super.getAns() == g) {
            return 2;
        }
        return 1;
    }
}
