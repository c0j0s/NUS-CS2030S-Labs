package cs2030.simulator;

public class ServerNotFoundException extends Exception {
    public ServerNotFoundException() {
    }

    // Constructor that accepts a message
    public ServerNotFoundException(String message) {
        super(message);
    }
}
