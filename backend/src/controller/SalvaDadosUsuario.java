package controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import model.UsuariosDAO;

public class SalvaDadosUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            SaveDataRequest pedidoDadosSalvar = gson.fromJson(inputString, SaveDataRequest.class);

            UsuariosDAO usuariosDAO = new UsuariosDAO();
            usuariosDAO.connect();

            if (pedidoDadosSalvar.data_to_save.equals("text")) {
                usuariosDAO.setPersonalText(pedidoDadosSalvar.login, pedidoDadosSalvar.text);
            } else if (pedidoDadosSalvar.data_to_save.equals("number")) {
                usuariosDAO.setNumero(pedidoDadosSalvar.login, pedidoDadosSalvar.number);
            } else {
                System.err.println("Erro ao processar pedido de salvar, dado desconhecido: " + pedidoDadosSalvar.data_to_save);;
            }

            usuariosDAO.close();

            exchange.sendResponseHeaders(200,-1);

            exchange.close();
        }
    }

    private static class SaveDataRequest {
        String login;
        String data_to_save;
        String text;
        int number;
    }
}