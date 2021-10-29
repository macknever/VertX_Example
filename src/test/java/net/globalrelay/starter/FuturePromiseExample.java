package net.globalrelay.starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {
    private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

    @Test
    void promise_success(Vertx vertx, VertxTestContext vertxTestContext) {
        final Promise<String> promise = Promise.promise();
        LOG.debug("Start");
        vertx.setTimer(500, id -> {
            promise.complete("success");
            LOG.debug("Success");
            vertxTestContext.completeNow();
        });
        LOG.debug("End");
    }

    @Test
    void promise_failure(Vertx vertx, VertxTestContext vertxTestContext) {
        final Promise<String> promise = Promise.promise();
        LOG.debug("Start");
        vertx.setTimer(500, id -> {
            promise.fail(new RuntimeException("Failure"));
            LOG.debug("Failed");
            vertxTestContext.completeNow();
        });
        LOG.debug("End");
    }

    @Test
    void future_success(Vertx vertx, VertxTestContext vertxTestContext) {
        final Promise<String> promise = Promise.promise();
        LOG.debug("Start");
        vertx.setTimer(500, id -> {
            promise.complete("Success Lawrence");
            LOG.debug("Success");
            vertxTestContext.completeNow();
        });

        final Future<String> future = promise.future();
        future
                .onSuccess(result -> {
                    LOG.debug("Result:  {}", result);
                    vertxTestContext.completeNow();
                })
                .onFailure(vertxTestContext::failNow);
        ;
        LOG.debug("End");
    }

    @Test
    void future_failure(Vertx vertx, VertxTestContext vertxTestContext) {
        final Promise<String> promise = Promise.promise();
        LOG.debug("Start");
        vertx.setTimer(500, id -> {
            promise.fail(new RuntimeException("Failed"));
            LOG.debug("Timer Done");

        });

        final Future<String> future = promise.future();
        future
                .onSuccess(vertxTestContext::failNow)

                .onFailure(error -> {
                    LOG.debug("result: ", error);
                    vertxTestContext.completeNow();
                });
        ;
        LOG.debug("End");
    }


}
