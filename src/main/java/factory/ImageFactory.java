package factory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import proxy_dao.Imagem;
import proxy_dao.ImagemProxy;
import model.Imagens;

public class ImageFactory {

    public ArrayList<ImagemProxy> getImagens() {
        Map mapImagens = Imagens.getInstance().getImagens();
        ArrayList<ImagemProxy> listaImagensProxy = new ArrayList<>();

        for (Object entry : mapImagens.entrySet()) {
            Entry<String, String> item = (Entry<String, String>) entry;
            String nome = item.getKey();
            String diretorio = item.getValue();
            Imagem img = new Imagem(nome, diretorio);

            ImagemProxy imgProxy = new ImagemProxy(img);

            listaImagensProxy.add(imgProxy);
        }

        return listaImagensProxy;
    }
}
