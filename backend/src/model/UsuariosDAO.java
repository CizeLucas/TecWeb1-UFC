package model;

import java.sql.*;
import java.util.ArrayList;

public class UsuariosDAO {  

    public static Connection connection;

    public void connect() {
        try {
            String url = "jdbc:sqlite:backend/db/dados.db";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Conexão com o base de dados estabelecida!");
            connection = conn;
        } catch (SQLException exception) {
            System.err.println("Erro ao conectar à base de dados: " + exception.getMessage());
            connection = null;
        }
    }

    public ArrayList<Usuario> getAllUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios")) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String senha_hash = resultSet.getString("senha_hash");
                boolean admin = resultSet.getBoolean("admin");
                int numero = resultSet.getInt("numero");
                String personalText = resultSet.getString("personal_text");

                Usuario usuario = new Usuario(login, senha_hash, admin, numero, personalText);
                usuarios.add(usuario);
            }
        } catch (SQLException exception) {
            System.err.println("Erro ao buscar usuários: " + exception.getMessage());
        }

        System.out.println("Sucesso ao retornar todos os usuários");
        return usuarios;
    }

    public Usuario getUsuario(String login) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        Usuario usuario = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senha_hash = resultSet.getString("senha_hash");
                boolean admin = resultSet.getBoolean("admin");
                int numero = resultSet.getInt("numero");
                String personalText = resultSet.getString("personal_text");

                usuario = new Usuario(login, senha_hash, admin, numero, personalText);
                System.out.println("Sucesso ao buscar usuário: " + login);
            }
        } catch (SQLException exception) {
            System.err.println("Erro ao buscar usuário: " + exception.getMessage());
        }

        return usuario;
    }

    public boolean verificaLogin(String login) {
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        boolean existe = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            }
        } catch (SQLException exception) {
            System.err.println("Erro ao verificar login: " + exception.getMessage());
        }

        System.out.println("Sucesso ao verificar existencia do login: " + login);
        return existe;
    }

    public boolean verificaSenha(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha_hash = ?";
        boolean senha_correta = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, SHA1.toHash(senha));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                senha_correta = true;
            }
        } catch (SQLException exception) {
            System.err.println("Erro ao verificar senha: " + exception.getMessage());
        }

        System.out.println("Sucesso ao verificar senha para login: " + login);
        return senha_correta;
    }

    public void addUsuario(String login, String senha) {
        String sql = "INSERT INTO usuarios (login, senha_hash) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, SHA1.toHash(senha));

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao adicionar novo usuario com login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao adicionar usuário: " + exception.getMessage());
        }
    }

    public void setNumero(String login, int numero) {
        String sql = "UPDATE usuarios SET numero = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, numero);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao editar o numero do login: " + login + ", para: " + numero);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar número: " + exception.getMessage());
        }
    }

    public void setPersonalText(String login, String personalText) {
        String sql = "UPDATE usuarios SET personal_text = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, personalText);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao editar o texto do login: " + login + ", para: " + personalText);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar texto pessoal: " + exception.getMessage());
        }
    }

    public void setAdmin(String login, boolean admin) {
        String sql = "UPDATE usuarios SET admin = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, admin);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao atualizar o status de admin do login: " + login + ", para: " + admin);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar admin: " + exception.getMessage());
        }
    }

    public void ChangeSenha(String login, String senha) {
        String sql = "UPDATE usuarios SET senha_hash = ? WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, SHA1.toHash(senha));
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            System.out.println("Sucesso ao alterar a senha do login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao atualizar senha: " + exception.getMessage());
        }
    }

    public void deleteUsuario(String login) {
        String sql = "DELETE FROM usuarios WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();

            System.out.println("Sucesso ao deletar registro do login: " + login);
        } catch (SQLException exception) {
            System.err.println("Erro ao deletar usuário: " + exception.getMessage());
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
