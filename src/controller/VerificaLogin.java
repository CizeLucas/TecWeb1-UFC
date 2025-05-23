package controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import model.UsuariosDAO;

public class VerificaLogin implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            LoginRequest pedidoLogin = gson.fromJson(inputString, LoginRequest.class);

            System.out.println("login recebido: " + pedidoLogin.login);
            System.out.println("senha recebida: " + pedidoLogin.senha);
        }
    }

    private static class LoginRequest {
        String login;
        String senha;
    }
}
