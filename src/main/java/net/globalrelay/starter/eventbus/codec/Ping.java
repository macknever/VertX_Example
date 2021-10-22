package net.globalrelay.starter.eventbus.codec;

public class Ping {
    private String message;
    private boolean enabled;

    public Ping() {
        // default constructor
    }

    public Ping(final String message, final boolean enabled) {
        this.message = message;
        this.enabled = enabled;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
