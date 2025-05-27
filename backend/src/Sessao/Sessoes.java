package Sessao;

import java.util.ArrayList;

public class Sessoes {

    private static ArrayList<Sessao> Sessoes = new ArrayList<Sessao>();

    public static void addSessao(Sessao sessao) {
        Sessoes.add(sessao);
    }

    public static void rmSessao(Sessao sessao) {
        Sessoes.remove(sessao);
    }

    public static void sair(String token) {
        for (Sessao sessao : Sessoes) {
            if (sessao.getToken().equals(token)) {
                sessao.sair();
            }
        }
    }

    public static String getLogin(String token) {
        for (Sessao sessao : Sessoes) {
            if (sessao.getToken().equals(token)) {
                return sessao.getLogin();
            }
        }

        return null;
    }
    
}
