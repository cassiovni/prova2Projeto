package proxy_dao;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Imagem {

    private String nome;
    private String diretorio;

    public Imagem(String nome, String diretorio) {
        this.nome = nome;
        this.diretorio = diretorio;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public Image getMiniatura() {
        File file = new File(this.diretorio);
        try {
            Image img = ImageIO.read(file);
            return img;
        } catch (Exception ex) {
            System.out.println("Não foi possível acessar");
        }
        return null;
    }

}
