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

                Publicacao.loadPublicacao(id, titulo, conteudo, usuarioLogin);
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

                publicacao = Publicacao.loadPublicacao(id, titulo, conteudo, usuarioLogin);
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

    public static int SalvarPublicacao(String titulo, String conteudo, String usuarioLogin) {
        int id = -1;
        
        connect();

        String sqlQuery = "INSERT INTO publicacoes (titulo, conteudo, usuario_login) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
            sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, conteudo);
            preparedStatement.setString(3, usuarioLogin);

            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao adicionar publicacao: " + exception.getMessage());
        }

        close();

        if (id != -1)
            System.out.println("Sucesso ao adicionar nova publicacao com id: " + id);
        else
            System.out.println("Erro estranho ao adicionar nova publicacao. nenhum id retornado");

        return id;
    }

}
