package ru.otus.killboss.client;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.killboss.KillBossServiceGrpc;
import ru.otus.killboss.RangeRequest;

public class KillBossClient {
    private static final Logger logger = LoggerFactory.getLogger(KillBossClient.class);
    private static final String HOST = "localhost";
    private static final int PORT = 50051;
    private static final long CLIENT_INCREMENT_TIMEOUT = 1000;
    private static final int CLIENT_INCREMENT_CYCLES = 50;
    private final KillBossServiceGrpc.KillBossServiceStub asyncStub;
    private final ClientStreamObserver responseStreamObserver;

    public KillBossClient() {
        Channel channel =
                ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        asyncStub = KillBossServiceGrpc.newStub(channel);
        responseStreamObserver = new ClientStreamObserver();
    }

    public static void main(String[] args) {

        int rangeStart = 0;
        int rangeEnd = 15;

        var request =
                RangeRequest.newBuilder().setStart(rangeStart).setEnd(rangeEnd).build();
        new KillBossClient().start(request);
    }

    private void start(RangeRequest rangeRequest) {
        asyncStub.getNumbers(rangeRequest, responseStreamObserver);
        logger.info("client is starting...");
        processIncrement();
    }

    private void processIncrement() {
        int clientValue = 0;
        int serverValue = 0;
        for (int i = 0; i < CLIENT_INCREMENT_CYCLES; i++) {
            if (responseStreamObserver.isCompleted()) {
                break;
            }
            int tempServerValue;
            if (serverValue != (tempServerValue = responseStreamObserver.getCurrentValue())) {
                serverValue = tempServerValue;
                clientValue += serverValue;
            }
            logger.info("currentValue:{}", clientValue);
            makeDelay();
            clientValue++;
        }
    }

    private void makeDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(CLIENT_INCREMENT_TIMEOUT);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
