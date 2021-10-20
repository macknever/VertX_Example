package net.globalrelay.vertx;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(rc -> {
            rc.response().end("Hello VertX World!");
        }).listen(8080);
    }

}
