class RubikLeft extends RubikFront {

    RubikLeft(Rubik r) {
        super(r);
    }

    @Override
    RubikLeft left() {
        return new RubikLeft(super.leftView().left().rightView());
    }

    @Override
    RubikLeft right() {
        return new RubikLeft(super.leftView().right().rightView());
    }

    @Override
    RubikLeft half() {
        return new RubikLeft(super.leftView().half().rightView());
    }

}
