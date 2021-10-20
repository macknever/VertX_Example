package net.globalrelay.vertx;

import org.jgroups.util.UUID;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.impl.AsyncResolveConnectHelper;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> start) {

        vertx.deployVerticle(new HelloVerticle());

        Router router = Router.router(vertx);

        router.get("/api/v1/hello").handler(this::helloVertXHandler);

        router.get("/api/v1/hello/:name").handler(this::helloNameHandler);

        ConfigStoreOptions defaultCOnfig = new ConfigStoreOptions().setType("file").setFormat("json")
                .setConfig(new JsonObject().put("path", "config.json"));

        ConfigRetrieverOptions opts = new ConfigRetrieverOptions().addStore(defaultCOnfig); // can add as many
                                                                                            // configStores as one wants

        Handler<AsyncResult<JsonObject>> handler = asyncResult -> this.handleConfigResults(start, router, asyncResult);
        ConfigRetriever cfgRetriever = ConfigRetriever.create(vertx, opts);
        cfgRetriever.getConfig(handler);

    }

    void handleConfigResults(Promise<Void> start, Router router, AsyncResult<JsonObject> asyncResult) {

        if (asyncResult.succeeded()) {
            JsonObject config = asyncResult.result();
            JsonObject http = config.getJsonObject("http");
            int httpPort = http.getInteger("port");
            vertx.createHttpServer().requestHandler(router).listen(httpPort);
            start.complete();
        } else {
            start.fail("Unable to load from configuration");
        }

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
