package Publicacoes;

import java.util.ArrayList;

import Arquivos.Arquivo;
import Usuario.Usuario;

public class Publicacao {

    private int id;
    private String titulo;
    private String conteudo;

    private Usuario usuario;
    private ArrayList<Arquivo> arquivos;

    private static ArrayList<Publicacao> publicacoes = new ArrayList<>();

    private Publicacao(int id, String titulo, String conteudo, String usuarioLogin) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.usuario = Usuario.getUsuarioLogin(usuarioLogin);
        this.arquivos = Arquivo.getAllArquivosWithPublicacaoId(id);

        publicacoes.add(this);
    }

    public static Publicacao loadPublicacao(int id, String titulo, String conteudo, String usuarioLogin) {
        for (Publicacao publicacao : publicacoes) {
            if (publicacao.id == id) {
                publicacao.titulo = titulo;
                publicacao.conteudo = conteudo;
                publicacao.usuario = Usuario.getUsuarioLogin(usuarioLogin);
                publicacao.arquivos = Arquivo.getAllArquivosWithPublicacaoId(id);
                return publicacao;
            }
        }

        return new Publicacao(id, titulo, conteudo, usuarioLogin);
    }

    public static Publicacao getPublicacaoId(int id) {
        for (Publicacao publicacao : publicacoes) {
            if (publicacao.id == id)
                return publicacao;
        }

        return PublicacoesDAO.getPublicacao(id);
    }

    public static ArrayList<Publicacao> getAllPublicacoes() {
        PublicacoesDAO.loadAllPublicacoes();

        return publicacoes;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ArrayList<Arquivo> getArquivos() {
        return arquivos;
    }

    public void addArquivo(Arquivo arquivo) {
        arquivos.add(arquivo);
    }
    
}
