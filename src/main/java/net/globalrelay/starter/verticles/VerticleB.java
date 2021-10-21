package net.globalrelay.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleB extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println(String.format("start %s",getClass().getName()));
        startPromise.complete();
    }
}
