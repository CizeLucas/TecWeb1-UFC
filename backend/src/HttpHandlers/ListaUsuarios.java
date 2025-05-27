package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Usuario.*;

import Sessao.Sessoes;

public class ListaUsuarios implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            InputStream input = exchange.getRequestBody();
            String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();
            AllUserDataRequest pedidoDados = gson.fromJson(inputString, AllUserDataRequest.class);
            AllUserDAtaResponse respostaDados = new AllUserDAtaResponse();

            String login_admin = Sessoes.getLogin(pedidoDados.token);

            int statusCode = 200;

            if (login_admin == null) {
                // token não pertence a nenhuma sessão ativa
                statusCode = 401; // não autorizado
                respostaDados.authentication = false;
            } else {
                UsuariosDAO usuariosDAO = new UsuariosDAO();
                usuariosDAO.connect();

                if (!usuariosDAO.getUsuario(login_admin).isAdmin()) {
                    // token pertence a uma sessão ativa, mas o usuário não é um admin
                    statusCode = 401; // não autorizado
                    respostaDados.authentication = false;
                } else {
                    // pedido é valido e lista de usuários é obtida
                    ArrayList<Usuario> listaUsuarios = usuariosDAO.getAllUsuarios();

                    respostaDados.array = new ArrayList<UserDataComplete>();

                    for (Usuario usuario : listaUsuarios) {
                        UserDataComplete usuarioData = new UserDataComplete();
                        
                        usuarioData.login = usuario.getLogin();
                        usuarioData.senha_hash = usuario.getSenha_hash();
                        usuarioData.texto = usuario.getPersonalText();
                        usuarioData.numero = usuario.getNumero();
                        usuarioData.admin = usuario.isAdmin();

                        respostaDados.array.add(usuarioData);
                    }
                }

                usuariosDAO.close();
            }

            String respostaJson = gson.toJson(respostaDados, AllUserDAtaResponse.class);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

            OutputStream respostaHttp = exchange.getResponseBody();
            respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
            respostaHttp.close();
        }  
    }

    private static class AllUserDataRequest {
        String token;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class AllUserDAtaResponse {
        ArrayList<UserDataComplete> array;

        boolean authentication = true;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class UserDataComplete {
        String login;
        String senha_hash;
        String texto;
        int numero;
        boolean admin;
    }
}
