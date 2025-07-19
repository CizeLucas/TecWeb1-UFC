package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;

import Publicacoes.Publicacao;
import Sessao.Sessoes;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SalvaPublicacao implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, 1);
            return;
        }

        InputStream input = exchange.getRequestBody();
        String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new Gson();
        SalvaPublicacaoRequest pedidoDados = gson.fromJson(inputString, SalvaPublicacaoRequest.class);
        SalvaPublicacaoResponse respostaDados = new SalvaPublicacaoResponse();

        String login = Sessoes.getLogin(pedidoDados.token);

        int statusCode = 200;

        if (login == null) {
            // token não pertence a nenhuma sessão ativa
            statusCode = 401; // não autorizado
            respostaDados.authentication = false;
        } else {
            // pedido é valido e uma nova publicação é salva
            Publicacao.SalvarNovaPublicacao(pedidoDados.titulo, pedidoDados.conteudo, login);
        }

        String respostaJson = gson.toJson(respostaDados, SalvaPublicacaoResponse.class);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

        OutputStream respostaHttp = exchange.getResponseBody();
        respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
        respostaHttp.close(); 
    }

    private class SalvaPublicacaoRequest {
        String token;

        String titulo;
        String conteudo;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private class SalvaPublicacaoResponse {
        boolean authentication = true;
    }
    
}
