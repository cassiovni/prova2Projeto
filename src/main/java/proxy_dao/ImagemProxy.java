package proxy_dao;

import java.awt.Image;
import model.Permissao;
import model.Usuario;

public class ImagemProxy {

    private Imagem imagem;
    private SQLitePermissao gerenciadorDeAcesso = SQLitePermissao.getInstance();

    public ImagemProxy(Imagem img) {
        this.imagem = img;
    }

    Image getMiniatura() {
        return imagem.getMiniatura();
    }

    public Imagem getImagem(Usuario usuario) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        if (gerenciadorDeAcesso.estaAutorizado(permissao)) {
            return new Imagem(imagem.getNome(), imagem.getDiretorio());
        }
        return null;
    }

    Imagem getImagem() {

        return new Imagem(imagem.getNome(), imagem.getDiretorio());

    }

    public boolean setDiretorio(Usuario usuario, String diretorio) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        if (gerenciadorDeAcesso.estaAutorizado(permissao)) {
            imagem.setDiretorio(diretorio);
            return true;
        }
        return false;
    }

    public String getDiretorio(Usuario usuario) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        if (gerenciadorDeAcesso.estaAutorizado(permissao)) {
            return imagem.getDiretorio();
        }
        return null;
    }

    public boolean setNome(Usuario usuario, String nome) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        if (gerenciadorDeAcesso.estaAutorizado(permissao)) {
            imagem.setNome(nome);
            return true;
        }
        return false;
    }

    public String getNome(Usuario usuario) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        if (gerenciadorDeAcesso.estaAutorizado(permissao)) {
            return imagem.getNome();
        }
        return null;
    }

    public boolean isAuthorized(Usuario usuario) {
        Permissao permissao = new Permissao(this.imagem, usuario);
        return gerenciadorDeAcesso.estaAutorizado(permissao);
    }

    public boolean setPermissao(Usuario usuarioAdmin, Usuario usuarioComum, boolean permissao) {
        Permissao perm = new Permissao(this.imagem, usuarioAdmin);
        if (gerenciadorDeAcesso.estaAutorizado(perm)) {
            if (permissao) {
                Permissao permissaoObj = new Permissao(imagem, usuarioComum);
                gerenciadorDeAcesso.insertPermissao(permissaoObj);
                return true;
            } else {
                Permissao permissaoObj = new Permissao(imagem, usuarioComum);
                gerenciadorDeAcesso.deletePermissao(permissaoObj);
                return true;
            }
        }
        return false;
    }

    //Memento
    public ImagemProxyMemento getImagemProxyMemento() {
        return new ImagemProxyMemento(new Imagem(this.imagem.getNome(), this.imagem.getDiretorio()));
    }

    public void restaura(ImagemProxyMemento imgProxyMemento) {
        this.imagem = imgProxyMemento.getImagem();
    }

}
