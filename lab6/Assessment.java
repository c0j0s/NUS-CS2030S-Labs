class Assessment implements Keyable {
    private final String type;
    private final String grade;

    Assessment(String type, String grade) {
        this.type = type;
        this.grade = grade;
    }

    String getGrade() {
        return grade;
    }

    @Override
    public String getKey() {
        return type;
    }
    
    @Override
    public String toString() {
        return String.format("{%s: %s}",type, grade);
    }
}
