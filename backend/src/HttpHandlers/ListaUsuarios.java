package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Usuario.*;

public class ListaUsuarios implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        ArrayUsuarios arrayUsuarios = new ArrayUsuarios();

        usuariosDAO.connect();
        ArrayList<Usuario> listaUsuarios = usuariosDAO.getAllUsuarios();
        usuariosDAO.close();

        arrayUsuarios.array = new ArrayList<UserDataComplete>();

        for (Usuario usuario : listaUsuarios) {
            UserDataComplete usuarioData = new UserDataComplete();
            
            usuarioData.login = usuario.getLogin();
            usuarioData.senha_hash = usuario.getSenha_hash();
            usuarioData.texto = usuario.getPersonalText();
            usuarioData.numero = usuario.getNumero();
            usuarioData.admin = usuario.isAdmin();

            arrayUsuarios.array.add(usuarioData);
        }

        Gson gson = new Gson();
        String respostaJson = gson.toJson(arrayUsuarios, ArrayUsuarios.class);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, respostaJson.getBytes(StandardCharsets.UTF_8).length);

        OutputStream respostaHttp = exchange.getResponseBody();
        respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
        respostaHttp.close();
    }

    private static class ArrayUsuarios {
        ArrayList<UserDataComplete> array;
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
