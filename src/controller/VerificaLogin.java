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

            String respostaJson;
            int statusCode = 200;

            if(usuariosDAO.verificaLogin(pedidoLogin.login)) {
                if (usuariosDAO.verificaSenha(pedidoLogin.login, pedidoLogin.senha)) {
                    System.out.println("senha correta para login: " + pedidoLogin.login);

                    respostaJson = "{\"login_existe\":1,\"senha_correta\":1}";
                } else {
                    System.out.println("senha incorreta para login: " + pedidoLogin.login);

                    respostaJson = "{\"login_existe\":1,\"senha_correta\":0}";
                }
            } else {
                System.out.println("login: " + pedidoLogin.login + ", não existe");

                respostaJson = "{\"login_existe\":0,\"senha_correta\":0}";
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }
    }

    private static class LoginRequest {
        String login;
        String senha;
    }
}
