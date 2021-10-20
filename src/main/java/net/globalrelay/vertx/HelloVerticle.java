package net.globalrelay.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public class HelloVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("hello.vertx.addr", this::helloVertxConsumer);

        vertx.eventBus().consumer("hello.named.addr", this::helloNamedConsumer);
    }

    void helloVertxConsumer(Message<String> msg) {
        msg.reply("Hello Vert.X World");
    }

    void helloNamedConsumer(Message<String> msg) {
        String name = msg.body().toString();
        msg.reply(String.format("Hello %s", name));
    }
}
