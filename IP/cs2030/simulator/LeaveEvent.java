package cs2030.simulator;

class LeaveEvent extends Event {

    LeaveEvent(Event event) {
        super(event, EventState.LEAVE);
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }

}
