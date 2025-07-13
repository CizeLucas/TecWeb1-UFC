package Arquivos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BancoDados.BasicDAO;

public class ArquivosDAO extends BasicDAO {

    public static ArrayList<Arquivo> loadAllArquivos() {
        ArrayList<Arquivo> arquivos = new ArrayList<>();

        connect();

        String sqlQuery = "SELECT * FROM arquivos";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomeArquivo = resultSet.getString("nome_arquivo");
                byte[] dados = resultSet.getBytes("conteudo");
                int publicacaoId = resultSet.getInt("publicacao_id");

                new Arquivo(nomeArquivo, dados, publicacaoId);
                System.out.println("Sucesso ao carregar todos os arquivo: " + nomeArquivo);
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao carregar todos os arquivo - " + exception.getMessage());
        }

        close();

        return arquivos;
    }

    public static Arquivo getArquivo(String nomeArquivo) {
        Arquivo arquivo = null;

        connect();

        String sqlQuery = "SELECT * FROM arquivos WHERE nome_arquivo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nomeArquivo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] dados = resultSet.getBytes("dados");
                int publicacao_id = resultSet.getInt("publicacao_id");

                arquivo = new Arquivo(nomeArquivo, dados, publicacao_id);
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao carregar arquivo: " + nomeArquivo + " - " + exception.getMessage());
        }

        close();

        if (arquivo != null)
            System.err.println("Sucesso ao carregar arquivo: " + nomeArquivo);
        else
            System.out.println("Nenhum arquivo de nome: " + nomeArquivo + " encontrado");

        return arquivo;
    }

}
