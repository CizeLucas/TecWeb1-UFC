package controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import model.Usuario;
import model.UsuariosDAO;

public class RetornaDadosUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            UserDataRequest pedidoDados = gson.fromJson(inputString, UserDataRequest.class);

            UsuariosDAO usuariosDAO = new UsuariosDAO();
            usuariosDAO.connect();

            UserData dadosUsuario = new UserData();
            int statusCode = 200;

            Usuario usuario = usuariosDAO.getUsuario(pedidoDados.login);
            dadosUsuario.login = usuario.getLogin();
            dadosUsuario.texto = usuario.getPersonalText();
            dadosUsuario.numero = usuario.getNumero();
            dadosUsuario.admin = usuario.isAdmin();

            usuariosDAO.close();

            String respostaJson = gson.toJson(dadosUsuario, UserData.class);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }
    }

    private static class UserDataRequest {
        String login;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class UserData {
        String login;
        String texto;
        int numero;
        boolean admin;
    }
}
