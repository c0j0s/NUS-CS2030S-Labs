class OffByOneGrader extends Grader {

    OffByOneGrader(int ans) {
        super(ans);
    }

    int compute(int g) {
        if(super.getAns() == g) {
            return 2;
        } else if (Math.abs(super.getAns() - g) == 1) {
            return 1;
        }
        return 0;
    }
}
