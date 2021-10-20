package net.globalrelay.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.get("/api/v1/hello").handler(rc -> {
            rc.request().response().end("Hello Vert.X World!");
        });

        router.get("/api/v1/hello/:name").handler(rc -> {

            String name = rc.pathParam("name");
            rc.request().response().end(String.format("Hello %s!", name));

        });

        vertx.createHttpServer().requestHandler(router).listen(8080);

    }

}
