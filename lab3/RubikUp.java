class RubikUp extends RubikFront {

    RubikUp(Rubik r) {
        super(r);
    }

    @Override
    RubikUp left() {
        return new RubikUp(super.upView().left().downView());
    }

    @Override
    RubikUp right() {
        return new RubikUp(super.upView().right().downView());
    }

    @Override
    RubikUp half() {
        return new RubikUp(super.upView().half().downView());
    }

}
