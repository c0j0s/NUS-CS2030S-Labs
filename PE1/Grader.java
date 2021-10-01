class Grader {
    private final int ans;

    Grader(int ans) {
        this.ans  = ans;
    }

    int getAns(){
        return ans;
    }

    int compute(int g) {
    	if(ans == g) {
	        return 1;
	    }
	    return 0;
    }
}
