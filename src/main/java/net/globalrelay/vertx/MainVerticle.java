package net.globalrelay.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.get("/api/v1/hello").handler(this::helloVertXHandler);

        router.get("/api/v1/hello/:name").handler(this::helloNameHandler);

        vertx.createHttpServer().requestHandler(router).listen(8080);

    }

    void helloVertXHandler(RoutingContext rc) {
        rc.request().response().end("Hello Vert.X World!");
    };

    void helloNameHandler(RoutingContext rc) {
        String name = rc.pathParam("name");
        rc.request().response().end(String.format("Hello %s!", name));
    }

}
