package ru.otus.killboss.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillBossServer {
    private static final Logger logger = LoggerFactory.getLogger(KillBossServer.class);
    private static final int PORT = 50051;
    private static final long SERVICE_STREAM_TIMEOUT = 2000;
    private final Server server;

    public KillBossServer() {
        server = ServerBuilder.forPort(PORT)
                .addService(new NumberService(SERVICE_STREAM_TIMEOUT))
                .build();
    }

    private void start() throws IOException, InterruptedException {
        server.start();
        logger.info("Server started");
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new KillBossServer().start();
    }
}
