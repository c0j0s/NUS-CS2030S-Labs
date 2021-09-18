class RubikDown extends RubikFront {

    RubikDown(Rubik r) {
        super(r);
    }

    @Override
    RubikDown left() {
        return new RubikDown(super.downView().left().upView());
    }

    @Override
    RubikDown right() {
        return new RubikDown(super.downView().right().upView());
    }

    @Override
    RubikDown half() {
        return new RubikDown(super.downView().half().upView());
    }

}
