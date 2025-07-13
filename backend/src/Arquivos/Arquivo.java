package Arquivos;

import java.util.ArrayList;
import Publicacoes.Publicacao;

public class Arquivo {

    private String nomeArquivo;
    private byte[] dados;

    private Publicacao publicacao;

    private static ArrayList<Arquivo> arquivos = new ArrayList<>();

    public Arquivo(String nomeArquivo, byte[] dados, int publicacao_id) {
        for (Arquivo arquivo : arquivos) {
            if (arquivo.nomeArquivo.equals(nomeArquivo)) {
                arquivo.dados = dados;
                arquivo.publicacao = Publicacao.getPublicacaoId(publicacao_id);
                return;
            }
        }

        this.nomeArquivo = nomeArquivo;
        this.dados = dados;
        this.publicacao = Publicacao.getPublicacaoId(publicacao_id);

        arquivos.add(this);

        if (!this.publicacao.getArquivos().contains(this))
            this.publicacao.addArquivo(this);
    }

    public static Arquivo getArquivo(String nomeArquivo) {
        for (Arquivo arquivo : arquivos) {
            if (arquivo.nomeArquivo.equals(nomeArquivo))
                return arquivo;
        }

        return ArquivosDAO.getArquivo(nomeArquivo);
    }

    public static ArrayList<Arquivo> getAllArquivos() {
        ArquivosDAO.loadAllArquivos();

        return arquivos;
    }

    public static ArrayList<Arquivo> getAllArquivosWithPublicacaoId(int publicacao_id) {
        ArquivosDAO.loadAllArquivos();

        ArrayList<Arquivo> arquivosId = new ArrayList<>();

        for (Arquivo arquivo : arquivos) {
            if (arquivo.publicacao.getId() == publicacao_id)
                arquivosId.add(arquivo);
        }

        return arquivosId;
    }

    public byte[] getDados() {
        return dados;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

}
