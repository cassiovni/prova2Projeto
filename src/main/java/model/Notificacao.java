package model;

import proxy_dao.ImagemProxy;

public class Notificacao {

    public static String TIPO_COMPARTILHAMENTO = "Compartilhamento";
    public static String TIPO_ACESSO = "Acesso";

    public static String STATUS_NOVA = "Nova";
    public static String STATUS_CONCLUIDA = "Concluida";

    private String tipo;
    private Usuario para;
    private ImagemProxy img;
    private String nomeImagem;
    private String status;

    public Notificacao(String tipo, Usuario para, ImagemProxy img, String status) {
        this.tipo = tipo;
        this.para = para;
        this.img = img;
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getPara() {
        return para;
    }

    public void setPara(Usuario para) {
        this.para = para;
    }

    public ImagemProxy getImg() {
        return img;
    }

    public void setImg(ImagemProxy img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    @Override
    public String toString() {
        return "Notificacao{" + "tipo=" + tipo + ", para=" + para + ", img=" + img + ", status=" + status + '}';
    }
}
