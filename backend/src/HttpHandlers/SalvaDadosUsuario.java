package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import Usuario.UsuariosDAO;

import Sessao.Sessoes;

public class SalvaDadosUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            SaveDataRequest pedidoDadosSalvar = gson.fromJson(inputString, SaveDataRequest.class);
            SaveDataResponse respostaDadosSalvar = new SaveDataResponse();

            String login = Sessoes.getLogin(pedidoDadosSalvar.token);

            int statusCode = 200;

            if (login == null) {
                // token não pertence a nenhuma sessão ativa
                statusCode = 401; // não autorizado
                respostaDadosSalvar.authentication = false;
            } else {
                UsuariosDAO usuariosDAO = new UsuariosDAO();
                usuariosDAO.connect();

                if (pedidoDadosSalvar.data_to_save.equals("text")) {
                    usuariosDAO.setPersonalText(login, pedidoDadosSalvar.text);
                } else if (pedidoDadosSalvar.data_to_save.equals("number")) {
                    usuariosDAO.setNumero(login, pedidoDadosSalvar.number);
                } else {
                    System.err.println("Erro ao processar pedido de salvar, dado desconhecido: " + pedidoDadosSalvar.data_to_save);;
                }

                usuariosDAO.close();
            }

            String respostaJson = gson.toJson(respostaDadosSalvar, SaveDataResponse.class);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }
    }

    private static class SaveDataRequest {
        String token;
        String data_to_save;
        String text;
        int number;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class SaveDataResponse {
        boolean authentication = true;
    }
}