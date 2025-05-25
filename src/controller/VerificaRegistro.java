package controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import model.UsuariosDAO;

public class VerificaRegistro implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            RegistroRequest pedidoRegistro = gson.fromJson(inputString, RegistroRequest.class);

            UsuariosDAO usuariosDAO = new UsuariosDAO();
            usuariosDAO.connect();

            RegistroResponse respostaRegistro = new RegistroResponse();
            int statusCode = 200;

            if (usuariosDAO.verificaLogin(pedidoRegistro.login)) {
                respostaRegistro.login_existe = true;
                respostaRegistro.senhas_iguais = false; // não importa
            } else {
                respostaRegistro.login_existe = false;
                if (pedidoRegistro.senha1.equals(pedidoRegistro.senha2)) {
                    respostaRegistro.senhas_iguais = true;
                } else {
                    respostaRegistro.senhas_iguais = false;
                }
            }

            usuariosDAO.close();

            String respostaJson = gson.toJson(respostaRegistro, RegistroResponse.class);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }   
    }

    private static class RegistroRequest {
        String login;
        String senha1;
        String senha2;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class RegistroResponse {
        boolean login_existe;
        boolean senhas_iguais;
    }
}
