package ru.otus.killboss;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillBossClient {
    private static final Logger logger = LoggerFactory.getLogger(KillBossClient.class);
    private final KillBossServiceGrpc.KillBossServiceStub asyncStub;

    public KillBossClient(String host, int port) {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        asyncStub = KillBossServiceGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        var killBossClient = new KillBossClient("localhost", 8080);
        var request = RangeRequest.newBuilder().setStart(0).setEnd(30).build();
        var responseStreamObserver = new ClientStreamObserver();
        logger.info("client is starting...");
        killBossClient.asyncStub.getNumbers(request, responseStreamObserver);
        int currentValue = 0;
        int serverValue = 0;
        for (int i = 0; i < 50; i++) {
            int tempServerValue;
            if (serverValue != (tempServerValue = responseStreamObserver.getCurrentValue())) {
                serverValue = tempServerValue;
                currentValue += serverValue;
            }
            logger.info("currentValue:{}", currentValue);
            TimeUnit.MILLISECONDS.sleep(1000);
            currentValue++;
        }
    }
}
