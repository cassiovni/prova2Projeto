package presenter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;
import view.TelaImagemExibicao;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PresenterImagemExibicao {

    private TelaImagemExibicao tela;
    private JFrame telaAnterior;

    public PresenterImagemExibicao(JFrame telaAnterior, String nomeImagem, String diretorioImagem) {
        this.telaAnterior = telaAnterior;
        this.tela = new TelaImagemExibicao();

        tela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                telaAnterior.setVisible(true);
            }
        });

        JButton botaoComImagem = getButtonWithImage(diretorioImagem);
        tela.getPanelImagemExibicao().setLayout(new FlowLayout());
        tela.getPanelImagemExibicao().add(botaoComImagem);
        tela.getPanelImagemExibicao().updateUI();
        tela.getPanelImagemExibicao().repaint();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
        tela.repaint();
        telaAnterior.setVisible(false);
    }

    public JButton getButtonWithImage(String diretorio) {
        try {
            System.out.println("dir:" + diretorio);
            File file = new File(diretorio);
            BufferedImage bimg = ImageIO.read(file);

            int width = bimg.getWidth();
            int height = bimg.getHeight();

            Image img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img);
            icon.setImage(icon.getImage().getScaledInstance(800, 600, 100));

            JButton bt = new JButton(icon);
            bt.setPreferredSize(new Dimension(800, 600));

            return bt;
        } catch (Exception ex) {
            System.out.println("Erro ao abrir imagem: " + diretorio);
        }
        return null;
    }

}
