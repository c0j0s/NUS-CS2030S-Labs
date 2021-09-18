class RubikBack extends RubikFront {

    RubikBack(Rubik r) {
        super(r);
    }

    @Override
    RubikBack left() {
        return new RubikBack(super.backView().left().backView());
    }

    @Override
    RubikBack right() {
        return new RubikBack(super.backView().right().backView());
    }

    @Override
    RubikBack half() {
        return new RubikBack(super.backView().half().backView());
    }
}
