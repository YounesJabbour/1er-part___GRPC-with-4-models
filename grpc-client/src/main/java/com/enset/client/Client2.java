package com.enset.client;

import com.enset.stubs.Bank;
import com.enset.stubs.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class Client2 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 5555).usePlaintext().build();

        BankServiceGrpc.BankServiceStub asyncStub = BankServiceGrpc.newStub(managedChannel);

        Bank.ConvertCurrencyRequest request = Bank.ConvertCurrencyRequest.newBuilder().setCurrencyFrom("MAD").setCurrencyTo("USD").setAmount(6500).build();

        asyncStub.convertCurrency(request, new StreamObserver<Bank.ConvertCurrencyResponse>() {

            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("*************************");
                System.out.println(convertCurrencyResponse);
                System.out.println("*************************");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }
        });
//        asyncStub.performStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
//            @Override
//            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
//                System.out.println("=============");
//                System.out.println(convertCurrencyResponse);
//                System.out.println("============");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//            throwable.getMessage();
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Completed");
//            }
//        });

        System.in.read();
    }
}
