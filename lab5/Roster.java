class Roster extends KeyableMap<Student> {

    Roster(String year) {
        super(year);
    }

    Roster(KeyableMap<Student> k) {
        super(k);
    }

    Roster put(Student item) {
        return new Roster(super.put(item));
    }

    String getGrade(String s, String m, String a) {
        try {
            return get(s).get(m).get(a).getGrade();
        } catch (Exception e) {
            return String.format("No such record: %s %s %s", s, m, a);
        }
    }
}
