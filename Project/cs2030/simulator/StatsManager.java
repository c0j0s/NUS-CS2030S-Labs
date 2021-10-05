package cs2030.simulator;

import java.util.List;

class StatsManager {
    private final int numServe;
    private final int numLeave;
    private final double waitTime;

    StatsManager() {
        this(0, 0, 0);
    }

    StatsManager(StatsManager st) {
        this(st.numServe, st.numLeave, st.waitTime);
    }

    StatsManager(int numServe, int numLeave, double waitTime) {
        this.numServe = numServe;
        this.numLeave = numLeave;
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", waitTime / numServe, numServe, numLeave);
    }

    StatsManager recordEvents(List<Event> events) {
        int i = numServe, j = numLeave;
        double k = waitTime;

        double custWating = 0;

        for (Event event : events) {
            switch (event.getState()) {
                case WAIT:
                    custWating = event.getTime();
                    break;
                case SERVE:
                    if (custWating != 0) {
                        k += event.getTime() - custWating;
                        custWating = 0;
                    }
                    break;
                case DONE:
                    i++;
                    break;
                case LEAVE:
                    j++;
                    break;
                default:
                    break;
            }
        }
        return new StatsManager(i, j, k);
    }
}
