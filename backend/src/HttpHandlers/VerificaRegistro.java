package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

import Usuario.UsuariosDAO;

public class VerificaRegistro implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, 1);
            return;
        }

        InputStream input = exchange.getRequestBody();
        String inputString = new String(input.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new Gson();
        RegistroRequest pedidoRegistro = gson.fromJson(inputString, RegistroRequest.class);

        RegistroResponse respostaRegistro = new RegistroResponse();
        int statusCode = 200;

        if (UsuariosDAO.verificaLogin(pedidoRegistro.login)) {
            respostaRegistro.login_existe = true;
        } else {
            respostaRegistro.login_existe = false;
            UsuariosDAO.addUsuario(pedidoRegistro.login, pedidoRegistro.senha);
        }

        String respostaJson = gson.toJson(respostaRegistro, RegistroResponse.class);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, respostaJson.getBytes(StandardCharsets.UTF_8).length);

        OutputStream respostaHttp = exchange.getResponseBody();
        respostaHttp.write(respostaJson.getBytes(StandardCharsets.UTF_8));
        respostaHttp.close();
    }

    private static class RegistroRequest {
        String login;
        String senha;
    }

    @SuppressWarnings("unused") // está sendo usado sim pelo gson.toJson
    private static class RegistroResponse {
        boolean login_existe;
    }
}
