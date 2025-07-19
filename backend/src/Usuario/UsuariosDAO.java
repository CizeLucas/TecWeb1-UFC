package Usuario;

import java.sql.*;

import BancoDados.BasicDAO;
import SHA1.SHA1;

public class UsuariosDAO extends BasicDAO {

    public static void loadAllUsuarios() {
        connect();

        String sqlQuery = "SELECT * FROM usuarios";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String senha_hash = resultSet.getString("senha_hash");
                boolean admin = resultSet.getBoolean("admin");

                Usuario.loadUsuario(login, senha_hash, admin);
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao carregar todos os usuários: " + exception.getMessage());
        }

        close();

        System.out.println("Sucesso ao carregar todos os usuários");
    }

    public static Usuario getUsuario(String login) {
        Usuario usuario = null;

        connect();

        String sqlQuery = "SELECT * FROM usuarios WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senha_hash = resultSet.getString("senha_hash");
                boolean admin = resultSet.getBoolean("admin");

                usuario = Usuario.loadUsuario(login, senha_hash, admin);
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao buscar usuário: " + exception.getMessage());
        }

        close();

        if (usuario != null)
            System.out.println("Sucesso ao buscar usuário: " + login);
        else
            System.out.println("Nenhum usuário com login: " + login + " encontrado");

        return usuario;
    }

    public static boolean verificaLogin(String login) {
        boolean existe = false;

        connect();

        String sqlQuery = "SELECT * FROM usuarios WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao verificar login: " + exception.getMessage());
        }

        close();

        System.out.println("Sucesso ao verificar existencia do login: " + login);
        return existe;
    }

    public static boolean verificaSenha(String login, String senha) {
        boolean senha_correta = false;

        connect();

        String sqlQuery = "SELECT * FROM usuarios WHERE login = ? AND senha_hash = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, SHA1.toHash(senha));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                senha_correta = true;
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao verificar senha: " + exception.getMessage());
        }

        close();

        System.out.println("Sucesso ao verificar senha para login: " + login);
        return senha_correta;
    }

    public static void addUsuario(String login, String senha) {
        connect();

        String sqlQuery = "INSERT INTO usuarios (login, senha_hash) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, SHA1.toHash(senha));

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao adicionar novo usuario com login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao adicionar usuário: " + exception.getMessage());
        }

        close();
    }

    public static void setAdmin(String login, boolean admin) {
        connect();

        String sqlQuery = "UPDATE usuarios SET admin = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setBoolean(1, admin);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao atualizar o status de admin do login: " + login + ", para: " + admin);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar admin: " + exception.getMessage());
        }

        close();
    }

    public static void ChangeSenha(String login, String senha) {
        connect();

        String sqlQuery = "UPDATE usuarios SET senha_hash = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, SHA1.toHash(senha));
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao alterar a senha do login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar senha: " + exception.getMessage());
        }

        close();
    }

    public static void deleteUsuario(String login) {
        connect();

        String sqlQuery = "DELETE FROM usuarios WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();

            System.out.println("Sucesso ao deletar registro do login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao deletar usuário: " + exception.getMessage());
        }

        close();
    }

}
