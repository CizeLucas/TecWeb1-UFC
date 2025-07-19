package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;

import Publicacoes.Publicacao;
import Sessao.Sessoes;

import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ListaPublicacoes implements HttpHandler {
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, 1);
            return;
        }

        InputStream input = exchange.getRequestBody();
        String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new Gson();
        ListaPublicacoesRequest pedidoDados = gson.fromJson(inputString, ListaPublicacoesRequest.class);
        ListaPublicacoesResponse respostaDados = new ListaPublicacoesResponse();

        String login = Sessoes.getLogin(pedidoDados.token);

        int statusCode = 200;

        if (login == null) {
            // token não pertence a nenhuma sessão ativa
            statusCode = 401; // não autorizado
            respostaDados.authentication = false;
        } else {
            // pedido é valido e lista de publicações é obtida
            ArrayList<Publicacao> listaPublicacoes = Publicacao.getAllPublicacoes();

            respostaDados.publicacoes = new ArrayList<PublicacaoResponse>();

            for (Publicacao publicacao : listaPublicacoes) {
                respostaDados.publicacoes.add(
                    new PublicacaoResponse(
                        publicacao.getTitulo(),
                        publicacao.getConteudo(),
                        publicacao.getUsuario().getLogin()
                    )
                );
            }
        }

        String respostaJson = gson.toJson(respostaDados, ListaPublicacoesResponse.class);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

        OutputStream respostaHttp = exchange.getResponseBody();
        respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
        respostaHttp.close(); 
    }

    private class ListaPublicacoesRequest {
        String token;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private class ListaPublicacoesResponse {
        ArrayList<PublicacaoResponse> publicacoes;

        boolean authentication = true;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private class PublicacaoResponse {
        String titulo;
        String conteudo;
        String loginUsuario;

        public PublicacaoResponse(
            String titulo,
            String conteudo,
            String loginUsuario
        ) {
            this.titulo = titulo;
            this.conteudo = conteudo;
            this.loginUsuario = loginUsuario;
        }
            
    }
    
}
