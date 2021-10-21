package net.globalrelay.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Point2PointExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(Point2PointExample.class);

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Point2PointExample());
        LOG.debug("start : {}", vertx.getClass().getName());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        startPromise.complete();
        vertx.deployVerticle(new Sender());
        vertx.deployVerticle(new Receiver());
    }

    static class Sender extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

        final String send_message = String.format("This is the message from %s", getClass().getName());

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            LOG.debug("Start sending");

            vertx.setPeriodic(100, id -> {
                vertx.eventBus().<String>send(Sender.class.getName(), String.format("Sending : %s", send_message));
            });
        }
    }

    static class Receiver extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
                LOG.debug("Received Message : {}", message.body().toString());
                message.reply("thx");
            });
        }
    }

}
