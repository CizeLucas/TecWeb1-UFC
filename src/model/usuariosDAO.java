package model;

import java.sql.*;

public class UsuariosDAO {  

    public static Connection connection;

    public void connect() {
        try {
            String url = "jdbc:sqlite:db/usuarios.db";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Conexão estabelecida!");
            connection = conn;
        } catch (SQLException exception) {
            System.out.println("Erro ao conectar à base de dados: " + exception.getMessage());
            connection = null;
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Encerrando a conexão com a base de dados...");
        } catch (SQLException exception) {
            System.out.println("Erro ao fechar a conexão: " + exception.getMessage());
        }
    }

}
