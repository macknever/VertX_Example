package net.globalrelay.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusMessaging extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(EventBusMessaging.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RequestVerticle());
        vertx.deployVerticle(new ResponseVerticle());
    }

    static class RequestVerticle extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(EventBusMessaging.class);
        static final String ADDRESS = "my.request.addr";


        @Override
        public void start(Promise<Void> startPromise) throws Exception {


            final JsonObject message = new JsonObject().put("id", 1).put("name", "Lawrence");

            LOG.debug("Sending: {}", message);
            EventBus eventBus = vertx.eventBus();
            eventBus.<JsonArray>request(ADDRESS, message, reply -> {
                LOG.debug("response:{}", reply.result().body());
            });

        }
    }

    static class ResponseVerticle extends AbstractVerticle {
        private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);


        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            // final String MESSAGE = String.format("I am %s, I received your message, THX", getClass().getName());

            vertx.eventBus().<JsonObject>consumer(RequestVerticle.ADDRESS, message -> {
                LOG.debug("Received message: {}", message.body());
                message.reply(new JsonArray().add("one").add("two").add("three"));
            });
        }
    }
}
