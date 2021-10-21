package net.globalrelay.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        System.out.println(String.format("start %s",getClass().getName()));
        vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
            System.out.println(String.format("Deploy %s",VerticleAA.class.getName()));
            vertx.undeploy(whenDeployed.result());
        });
        vertx.deployVerticle(new VerticleAB(),whenDeployed -> {
            System.out.println(String.format("Deploy %s",VerticleAB.class.getName()));
        });
        startPromise.complete();
    }
}
