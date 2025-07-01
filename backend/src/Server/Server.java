package Server;

import com.sun.net.httpserver.HttpServer;

import HttpHandlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/VerificaLogin", new VerificaLogin());

            server.createContext("/", new StaticFileHandler());

            server.createContext("/VerificaRegistro", new VerificaRegistro());

            server.createContext("/RetornaDadosUsuario", new RetornaDadosUsuario());

            server.createContext("/SalvaDadosUsuario", new SalvaDadosUsuario());

            server.createContext("/ListaUsuarios", new ListaUsuarios());

            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Servidor iniciado em http://localhost:8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
