package controller;

import model.usuariosDAO;

public class Main {

    public static void main(String[] args) {
        usuariosDAO usuariosDAOInstance = new usuariosDAO();

        usuariosDAOInstance.connect();
        usuariosDAOInstance.close();
    }

}
