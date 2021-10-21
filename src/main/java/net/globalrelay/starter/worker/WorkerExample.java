package net.globalrelay.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WorkerExample());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        LOG.debug("start {}",getClass().getName());
        vertx.deployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("my worker-pool")
        );
        startPromise.complete();
        executeBlocking();
    }

    private void executeBlocking() {
        vertx.executeBlocking(event -> {
            LOG.debug("Executing blocking code");
            try {
                Thread.sleep(5000);
                event.complete();
            } catch (InterruptedException ie){
                LOG.error("Failed:" ,ie);
                event.fail(ie);
            }
        },asyncResult -> {
            if (asyncResult.succeeded()){
                LOG.debug("Blocking call done");
            } else {
                LOG.error("Blocking call fail due to:", asyncResult.cause());
            }
        });
    }
}
