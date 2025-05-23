package model;

public class Usuario {

    private String login;
    private String senha_hash;
    private boolean admin;

    private int numero;
    private String personalText;

    public Usuario(String login, String senha_hash, boolean admin, int numero, String personalText) {
        this.login = login;
        this.senha_hash = senha_hash;
        this.admin = admin;

        this.numero = numero;
        this.personalText = personalText;
    }

    // getters
    public String getLogin() {
        return login;
    }

    public boolean VerificaSenha(String senha) {
        return SHA1.toHash(senha).equals(senha_hash);
    }

    public boolean isAdmin() {
        return admin;
    }

    public int getNumero() {
        return numero;
    }

    public String getPersonalText() {
        return personalText;
    }
    
    // setters
    public void setSenha_hash(String senha) {
        this.senha_hash = SHA1.toHash(senha);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPersonalText(String personalText) {
        this.personalText = personalText;
    }
}
