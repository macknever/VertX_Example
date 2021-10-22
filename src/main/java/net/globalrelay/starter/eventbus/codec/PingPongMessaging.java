package net.globalrelay.starter.eventbus.codec;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongMessaging extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(PingPongMessaging.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new PingVerticle(), logOnError());
        vertx.deployVerticle(new PongVerticle(), logOnError());
    }

    private static Handler<AsyncResult<String>> logOnError() {
        return ar -> {
            if (ar.failed()) {
                LOG.error("error: ", ar.cause());
            }

        };
    }

    static class PingVerticle extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);
        static final String ADDRESS = PingVerticle.class.getName();


        @Override
        public void start(Promise<Void> startPromise) throws Exception {

            // startPromise.complete();
            EventBus eventBus = vertx.eventBus();
            final Ping message = new Ping("Hello Vertx from Ping", true);
            LOG.debug("Sending: {}", message);
            // register only once
            eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
            eventBus.<Pong>request(ADDRESS, message, reply -> {
                if (reply.failed()) {
                    LOG.error("error: ", reply.cause());
                    return;
                }
                LOG.debug("response:{}", reply.result().body());
            });
            startPromise.complete();

        }
    }

    static class PongVerticle extends AbstractVerticle {
        private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);


        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            // final String MESSAGE = String.format("I am %s, I received your message, THX", getClass().getName());
            vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
            vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
                LOG.debug("Received message: {}", message.body());
                message.reply(new Pong(1));
            }).exceptionHandler(error -> {
                LOG.error("error: ", error);
            });
            startPromise.complete();
        }
    }
}
