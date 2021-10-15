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
        return get(s).flatMap(st -> st.get(m))
                .flatMap(md -> md.get(a))
                .map(g -> g.getGrade())
                .orElse(String.format("No such record: %s %s %s", s, m, a));
    }
}
