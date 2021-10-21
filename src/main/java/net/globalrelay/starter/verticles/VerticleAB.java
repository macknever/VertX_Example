package net.globalrelay.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println(String.format("start %s",getClass().getName()));
        startPromise.complete();
    }
    @Override
    public void stop(final Promise<Void> stopPromise) throws Exception {
        System.out.println(String.format("Stop %s", getClass().getName()));
        stopPromise.complete();
    }
}
