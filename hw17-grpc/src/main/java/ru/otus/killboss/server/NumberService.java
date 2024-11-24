package ru.otus.killboss.server;

import io.grpc.stub.StreamObserver;
import ru.otus.killboss.KillBossServiceGrpc;
import ru.otus.killboss.NumberResponse;
import ru.otus.killboss.RangeRequest;

import java.util.concurrent.TimeUnit;

public class NumberService extends KillBossServiceGrpc.KillBossServiceImplBase {

    private final long delay;

    public NumberService(long delay) {
        this.delay = delay;
    }

    private void makeDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
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
            makeDelay();
        }
        responseObserver.onCompleted();
    }
}
