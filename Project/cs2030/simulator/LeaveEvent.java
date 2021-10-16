package cs2030.simulator;

class LeaveEvent extends Event {

    LeaveEvent(Event event) {
        super(EventState.LEAVE, event.getTime(), event.getCustomer());
    }

    @Override
    public String toString() {
        return super.toString() + " leaves";
    }

}
