package net.globalrelay.starter.eventbus.codec;

public class Pong {
    private Integer id;

    public Pong() {
    }

    public Pong(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}