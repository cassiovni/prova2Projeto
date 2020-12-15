package model;

import proxy_dao.Imagem;

public class Permissao {

    private Imagem imagem;
    private Usuario pesssoa;

    public Permissao(Imagem imagem, Usuario pesssoa) {
        this.imagem = imagem;
        this.pesssoa = pesssoa;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Usuario getPesssoa() {
        return pesssoa;
    }

    public void setPesssoa(Usuario pesssoa) {
        this.pesssoa = pesssoa;
    }

}
