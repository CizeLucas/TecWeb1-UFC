package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import Usuario.UsuariosDAO;

import Sessao.Sessao;

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

            LoginResponse respostaRegistro = new LoginResponse();
            int statusCode = 200;

            if(usuariosDAO.verificaLogin(pedidoLogin.login)) {
                respostaRegistro.login_existe = true;
                if (usuariosDAO.verificaSenha(pedidoLogin.login, pedidoLogin.senha)) {
                    System.out.println("senha correta para login: " + pedidoLogin.login);

                    Sessao novaSessao = new Sessao(pedidoLogin.login);

                    respostaRegistro.senha_correta = true;
                    respostaRegistro.token = novaSessao.getToken();
                } else {
                    System.out.println("senha incorreta para login: " + pedidoLogin.login);

                    respostaRegistro.senha_correta = false;
                }
            } else {
                System.out.println("login: " + pedidoLogin.login + ", não existe");

                respostaRegistro.login_existe = false;
                respostaRegistro.senha_correta = false; // não importa
            }

            usuariosDAO.close();

            String respostaJson = gson.toJson(respostaRegistro, LoginResponse.class);

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

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class LoginResponse {
        boolean login_existe;
        boolean senha_correta;
        String token;
    }
}
