class Module extends KeyableMap<Assessment> {

    Module(String code) {
        super(code);
    }

    Module(KeyableMap<Assessment> k) {
        super(k);
    }

    Module put(Assessment item) {
        return new Module(super.put(item));
    }

}
