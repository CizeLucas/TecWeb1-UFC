package Sessao;

import java.security.SecureRandom;
import java.util.HexFormat;

public class Sessao {

    private String token;
    private String login;

    public Sessao(String login) {
        do {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[32];
            random.nextBytes(bytes);

            this.token = HexFormat.of().formatHex(bytes);
        } while (Sessoes.ExisteToken(token));

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
