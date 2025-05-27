package HttpHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Responsavel por receber os links das paginas que o usuário quer acessar e enviar os arquivos certos
 */
public class StaticFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        File file = new File("." + path);
        if (file.exists() && !file.isDirectory()) {
            String mime = "text/html";
            if (path.endsWith(".css")) mime = "text/css";
            if (path.endsWith(".js")) mime = "application/javascript";
            exchange.getResponseHeaders().set("Content-Type", mime);
            byte[] bytes = Files.readAllBytes(file.toPath());
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } else {
            String notFound = "404 Not Found";
            exchange.sendResponseHeaders(404, notFound.length());
            OutputStream os = exchange.getResponseBody();
            os.write(notFound.getBytes());
            os.close();
        }
    }
}
