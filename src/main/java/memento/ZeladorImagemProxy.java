package memento;

import java.util.ArrayList;
import proxy_dao.ImagemProxyMemento;

public class ZeladorImagemProxy {

    private ArrayList<ImagemProxyMemento> estados;

    public ZeladorImagemProxy() {
        this.estados = new ArrayList<>();
    }

    public void addPessoaMemento(ImagemProxyMemento imgProMememento) {
        this.estados.add(imgProMememento);

    }

    public ImagemProxyMemento getUltimoEstado() {
        if (estados.size() > 0) {
            ImagemProxyMemento imgProMemento = estados.get(estados.size() - 1);
            estados.remove(estados.size() - 1);
            return imgProMemento;
        } else {
            return null;
        }
    }

    public boolean containsEstados() {
        return (estados.size() > 0);
    }

    public void reiniciar() {
        this.estados = new ArrayList<>();
    }

}
