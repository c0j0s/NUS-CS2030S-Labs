class RubikRight extends RubikFront {

    RubikRight(Rubik r) {
        super(r);
    }

    @Override
    RubikRight left() {
        return new RubikRight(super.rightView().left().leftView());
    }

    @Override
    RubikRight right() {
        return new RubikRight(super.rightView().right().leftView());
    }

    @Override
    RubikRight half() {
        return new RubikRight(super.rightView().half().leftView());
    }

}
