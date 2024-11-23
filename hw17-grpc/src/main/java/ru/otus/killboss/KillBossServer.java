package ru.otus.killboss;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillBossServer {
    private static final Logger logger = LoggerFactory.getLogger(KillBossServer.class);

    private final Server server;

    public KillBossServer(int port) {
        server = ServerBuilder.forPort(port).addService(new NumberService()).build();
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        logger.info("Server started");
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new KillBossServer(8080).start();
    }

    private static class NumberService extends KillBossServiceGrpc.KillBossServiceImplBase {

        private static void sleepTwoSeconds() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void getNumbers(RangeRequest request, StreamObserver<NumberResponse> responseObserver) {
            int start = request.getStart();
            int end = request.getEnd();
            for (int i = start; i <= end; i++) {
                responseObserver.onNext(NumberResponse.newBuilder().setValue(i).build());
                sleepTwoSeconds();
            }
            responseObserver.onCompleted();
        }
    }
}
