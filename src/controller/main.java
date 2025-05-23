package controller;

import model.usuariosDAO;

public class main {

    public static void main(String[] args) {
        usuariosDAO usuariosDAOInstance = new usuariosDAO();

        usuariosDAOInstance.connect();
        usuariosDAOInstance.close();
    }

}
