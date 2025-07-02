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
        String basePath = "frontend";
        String requestedPath = exchange.getRequestURI().getPath();

        // Redireciona raiz para landing_page.html
        if (requestedPath.equals("/")) {
            requestedPath = "/landing_page.html";
        }

        // Tenta primeiro em frontend
        File file = new File(basePath + requestedPath);

        // Se não existir, tenta em frontend/src
        if (!file.exists() || file.isDirectory()) {
            file = new File(basePath + "/src" + requestedPath);
        }

        // Se não existir, tenta em frontend/src/site
        if (!file.exists() || file.isDirectory()) {
            file = new File(basePath + "/src/site" + requestedPath);
        }

        if (file.exists() && !file.isDirectory()) {
            String mime = "text/html";
            if (requestedPath.endsWith(".css")) mime = "text/css";
            if (requestedPath.endsWith(".js")) mime = "application/javascript";
            if (requestedPath.endsWith(".svg")) mime = "image/svg+xml";
            if (requestedPath.endsWith(".png")) mime = "image/png";
            if (requestedPath.endsWith(".jpg") || requestedPath.endsWith(".jpeg")) mime = "image/jpeg";
            if (requestedPath.endsWith(".ico")) mime = "image/x-icon";
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
