package by.lvl.hexmap.api.events;

public class FailedEvent {

    private String message;

    public FailedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
