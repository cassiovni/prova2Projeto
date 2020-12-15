package proxy_dao;

public class ImagemProxyMemento {
    private Imagem imagem;

    ImagemProxyMemento(Imagem img) {
        this.imagem = img;
    }

    Imagem getImagem() {
        return imagem;
    }

}
