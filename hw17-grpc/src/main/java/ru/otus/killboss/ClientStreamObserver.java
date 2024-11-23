package ru.otus.killboss;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);
    private int currentValue = 0;

    @Override
    public void onNext(NumberResponse numberResponse) {
        currentValue = numberResponse.getValue();
        logger.info("newValue:{}", currentValue);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error", throwable);
    }

    @Override
    public void onCompleted() {
        logger.info("request completed");
    }

    public int getCurrentValue() {
        return currentValue;
    }
}
