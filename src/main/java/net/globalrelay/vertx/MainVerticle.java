package net.globalrelay.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {

        vertx.deployVerticle(new HelloVerticle());

        Router router = Router.router(vertx);

        router.get("/api/v1/hello").handler(this::helloVertXHandler);

        router.get("/api/v1/hello/:name").handler(this::helloNameHandler);

        vertx.createHttpServer().requestHandler(router).listen(8080);

    }

    void helloVertXHandler(RoutingContext rc) {
        vertx.eventBus().request("hello.vertx.addr", "", reply -> {
            rc.request().response().end(reply.result().body().toString());
        });
    };

    void helloNameHandler(RoutingContext rc) {
        String name = rc.pathParam("name");
        vertx.eventBus().request("hello.named.addr", name, reply -> {
            rc.request().response().end(reply.result().body().toString());
        });
    }

}
