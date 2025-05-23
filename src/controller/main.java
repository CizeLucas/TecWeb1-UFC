package controller;

import model.UsuariosDAO;

public class Main {

    public static void main(String[] args) {
        UsuariosDAO usuariosDAOInstance = new UsuariosDAO();

        usuariosDAOInstance.connect();
        usuariosDAOInstance.close();
    }

}
