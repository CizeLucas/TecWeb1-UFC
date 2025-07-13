package Publicacoes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BancoDados.BasicDAO;

public class PublicacoesDAO extends BasicDAO {

    public static void loadAllPublicacoes() {
        connect();

        String sqlQuery = "SELECT * FROM publicacoes";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String conteudo = resultSet.getString("conteudo");
                String usuarioLogin = resultSet.getString("usuario_login");

                new Publicacao(id, titulo, conteudo, usuarioLogin);
                System.out.println("Sucesso ao carregar publicação de id: " + Integer.toString(id));
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao carregar todos as publicações - " + exception.getMessage());
        }

        close();
    }
    
    public static Publicacao getPublicacao(int id) {        
        Publicacao publicacao = null;

        connect();

        String sqlQuery = "SELECT * FROM publicacoes WHERE id = ?";
        String idStr = Integer.toString(id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, idStr);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                String conteudo = resultSet.getString("conteudo");
                String usuarioLogin = resultSet.getString("usuario_login");

                publicacao = new Publicacao(id, titulo, conteudo, usuarioLogin);
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao buscar publicação de id: " + idStr + " - " + exception.getMessage());
        }

        close();

        if (publicacao != null)
            System.out.println("Sucesso ao buscar publicação de id: " + idStr);
        else
            System.out.println("Nenhuma publicação de id: " + idStr);

        return publicacao;
    }

}
