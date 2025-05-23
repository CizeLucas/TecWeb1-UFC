package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.UsuariosDAO;

@WebServlet("/VerificaLogin")
public class VerificaLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        System.out.println("login enviado: " + login);
        System.out.println("senha enviada: " + senha);

        UsuariosDAO usuariosDAO = new UsuariosDAO();
        usuariosDAO.connect();

        if (usuariosDAO.verificaLogin(login)) {
            if (usuariosDAO.verificaSenha(login, senha)) {
                // login válido
                // redirecionar para a pagina pessoal
                System.out.println("Login válido");
            } else {
                // senha errada
                // pedir para o usuario tentar novamente
                System.out.println("Senha errada");
            }
        } else {
            System.out.println("Login não existe");
            // login não existe
            // perguntar se o login está certo ou se o usuario quer criar um novo login
        }

        usuariosDAO.close();
    }
}
