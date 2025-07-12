package Usuario;

import SHA1.SHA1;

public class Usuario {

    private String login;
    private String senha_hash;
    private boolean admin;

    public Usuario(String login, String senha_hash, boolean admin) {
        this.login = login;
        this.senha_hash = senha_hash;
        this.admin = admin;
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
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
