package com.enset.client;

import com.enset.stubs.Bank;
import com.enset.stubs.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Client4 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 5555).usePlaintext().build();

        BankServiceGrpc.BankServiceStub asyncStub = BankServiceGrpc.newStub(managedChannel);


        StreamObserver<Bank.ConvertCurrencyRequest> streamObserver = asyncStub.fullCurrencyStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("=============");
                System.out.println(convertCurrencyResponse);
                System.out.println("============");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.getMessage();
            }

            @Override
            public void onCompleted() {
                System.out.println("FIN");
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int counter = 0;

            @Override
            public void run() {
                Bank.ConvertCurrencyRequest request = Bank.ConvertCurrencyRequest.newBuilder().setAmount(Math.random() * 5000).build();
                streamObserver.onNext(request);
                ++counter;
                System.out.println("Counter = " + counter);
                if (counter == 20) {
                    streamObserver.onCompleted();
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }
}
