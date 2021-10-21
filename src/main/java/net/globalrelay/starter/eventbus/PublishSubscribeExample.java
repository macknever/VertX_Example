package net.globalrelay.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class PublishSubscribeExample extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new PublishSubscribeExample());
        vertx.deployVerticle(new Publish());
        vertx.deployVerticle(new Subcriber1());
        vertx.deployVerticle(new Subcriber2());
        vertx.deployVerticle(Subcriber1.class.getName(), new DeploymentOptions().setInstances(3));
    }

    static class Publish extends AbstractVerticle {

        private static final Logger LOG = LoggerFactory.getLogger(PublishSubscribeExample.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();

            LOG.debug("message published from :{}", getClass().getName());
            vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id -> {
                vertx.eventBus().publish(Publish.class.getName(), "message for all");
            });

        }

    }

    public static class Subcriber1 extends AbstractVerticle {
        private static final Logger LOG = LoggerFactory.getLogger(Subcriber1.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().<String>consumer(Publish.class.getName(), Message -> {
                LOG.debug("Received Message: {}", Message.body().toString());
            });
        }
    }

    static class Subcriber2 extends AbstractVerticle {
        private static final Logger LOG = LoggerFactory.getLogger(Subcriber2.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().<String>consumer(Publish.class.getName(), Message -> {
                LOG.debug("Received Message: {}", Message.body().toString());
            });
        }
    }
}
