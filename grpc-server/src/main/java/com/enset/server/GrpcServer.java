package com.enset.server;

import com.enset.service.BankGrpcService;
import io.grpc.Server;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = io.grpc.ServerBuilder.forPort(5555)
                .addService(new BankGrpcService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
