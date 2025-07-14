package Usuario;

import java.util.ArrayList;

import SHA1.SHA1;

public class Usuario {

    private String login;
    private String senha_hash;
    private boolean admin;

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    private Usuario(String login, String senha_hash, boolean admin) {
        this.login = login;
        this.senha_hash = senha_hash;
        this.admin = admin;

        usuarios.add(this);
    }

    public static Usuario loadUsuario(String login, String senha_hash, boolean admin) {
        for (Usuario usuario : usuarios) {
            if (usuario.login.equals(login)) {
                usuario.senha_hash = senha_hash;
                usuario.admin = admin;
                return usuario;
            }
        }

        return new Usuario(login, senha_hash, admin);
    }

    public static Usuario getUsuarioLogin(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.login.equals(login))
                return usuario;
        }

        return UsuariosDAO.getUsuario(login);
    }

    public static ArrayList<Usuario> getAllUsuarios() {
        UsuariosDAO.loadAllUsuarios();

        return usuarios;
    }

    // getters
    public String getLogin() {
        return login;
    }

    public String getSenha_hash() {
        return senha_hash;
    }

    public boolean VerificaSenha(String senha) {
        return SHA1.toHash(senha).equals(senha_hash);
    }

    public boolean isAdmin() {
        return admin;
    }
    
    // setters
    public void setSenha_hash(String senha) {
        this.senha_hash = SHA1.toHash(senha);

        UsuariosDAO.ChangeSenha(this.login, senha);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;

        UsuariosDAO.setAdmin(this.login, admin);
    }
}
