package model;

import proxy_dao.Imagem;
import proxy_dao.ImagemProxy;
import proxy_dao.SQLiteImagem;
import java.util.HashMap;

public class Imagens {

    private static Imagens instance;
    private SQLiteImagem databaseImagens;

    private Imagens() {
        databaseImagens = SQLiteImagem.getInstance();
    }

    public static Imagens getInstance() {
        if (instance == null) {
            instance = new Imagens();
        }
        return instance;
    }

    public void insertImagem(Imagem imagem) {
        databaseImagens.insertImagem(imagem);
    }

    public void deleteImagem(ImagemProxy imagemProxy) {
        databaseImagens.deleteImagem(imagemProxy);
    }

    public HashMap<String, String> getImagens() {
        return databaseImagens.getImagens();
    }
    
    public ImagemProxy getImagem(String nome) {
        return databaseImagens.getImagem(nome);
    }

    public ImagemProxy getImagemNome(String nome) {
        return databaseImagens.getImagem(nome);
    }

}
