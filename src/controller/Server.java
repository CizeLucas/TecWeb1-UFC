package controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/VerificaLogin", new VerificaLogin());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Servidor iniciado em http://localhost:8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
