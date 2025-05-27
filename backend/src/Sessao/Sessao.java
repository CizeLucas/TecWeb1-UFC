package Sessao;

import SHA1.SHA1;

public class Sessao {
    
    private static int count = 0;

    private String token;
    private String login;

    public Sessao(String login) {
        count++;
        this.token = SHA1.toHash(String.valueOf(count));
        this.login = login;

        System.out.println("nova sessão iniciada com login: " + login + " e token: " + token);

        Sessoes.addSessao(this);
    }

    public void sair() {
        System.out.println("Sessão com login: " + login + " finalizada.");

        Sessoes.rmSessao(this);
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
