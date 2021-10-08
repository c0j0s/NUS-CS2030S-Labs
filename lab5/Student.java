class Student extends KeyableMap<Module> {

    Student(String name) {
        super(name);
    }

    Student(KeyableMap<Module> k) {
        super(k);
    }

    Student put(Module item) {
        return new Student(super.put(item));
    }

}
