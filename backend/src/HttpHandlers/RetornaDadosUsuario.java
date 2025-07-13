package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import Usuario.*;

import Sessao.Sessoes;

public class RetornaDadosUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            UserDataRequest pedidoDados = gson.fromJson(inputString, UserDataRequest.class);
            UserDataResponse respostaDadosUsuario = new UserDataResponse();

            String login = Sessoes.getLogin(pedidoDados.token);

            int statusCode = 200;

            if (login == null) {
                // token não pertence a nenhuma sessão ativa portanto os dados não são retornados
                statusCode = 401; // não autorizado
                respostaDadosUsuario.authentication = false;
            } else {
                Usuario usuario = Usuario.getUsuarioLogin(login);

                respostaDadosUsuario.login = usuario.getLogin();
                respostaDadosUsuario.admin = usuario.isAdmin();
            }

            String respostaJson = gson.toJson(respostaDadosUsuario, UserDataResponse.class);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }
    }

    private static class UserDataRequest {
        String token;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class UserDataResponse {
        String login;
        boolean admin;

        boolean authentication = true;
    }
}
