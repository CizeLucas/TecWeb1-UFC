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

            UsuariosDAO usuariosDAO = new UsuariosDAO();
            usuariosDAO.connect();

            if(usuariosDAO.verificaLogin(pedidoLogin.login)) {
                if (usuariosDAO.verificaSenha(pedidoLogin.login, pedidoLogin.senha)) {
                    System.out.println("senha correta para login: " + pedidoLogin.login);
                } else {
                    System.out.println("senha incorreta para login: " + pedidoLogin.login);
                }
            } else {
                System.out.println("login: " + pedidoLogin.login + ", não existe");
            }
            
        }
    }

    private static class LoginRequest {
        String login;
        String senha;
    }
}
