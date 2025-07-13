package BancoDados;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class BasicDAO {

    public static Connection connection;

    public void connect() {
        try {
            Properties databaseConfig = new Properties();
            FileInputStream fis = new FileInputStream("backend/config/dbconfig.properties");
            databaseConfig.load(fis);

            String url = databaseConfig.getProperty("db.url");
            String user = databaseConfig.getProperty("db.user");
            String password = databaseConfig.getProperty("db.password");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o base de dados estabelecida!");
            connection = conn;
        } catch (SQLException exception) {
            System.err.println("Erro ao conectar à base de dados: " + exception.getMessage());
            connection = null;
        } catch (IOException exception) {
            System.err.println("Erro ao carregar dados de acesso ao banco de dados:" + exception.getMessage());
            connection = null;
        } 
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Encerrando a conexão com a base de dados...");
        } catch (SQLException exception) {
            System.err.println("Erro ao fechar a conexão: " + exception.getMessage());
        }
    }

}
