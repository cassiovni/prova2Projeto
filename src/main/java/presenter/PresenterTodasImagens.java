package presenter;

import factory.ImageFactory;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import memento.ZeladorImagemProxy;
import model.Usuario;
import proxy_dao.ImagemProxy;
import proxy_dao.JButtonImage;
import state.StateTodasImagensAdminPermissao;
import state.StateTodasImagensLista;
import state.StateTodasImagensPermissao;
import state.StateTodasImagens;
import view.TelaTodasImagens;

public class PresenterTodasImagens {

    public static int STATE_VISUALIZAR = 0;
    public static int STATE_PERMISSAO = 1;
    public static int STATE_ADMIN_PERMISSAO = 2;

    private JButtonImage lastButtonPressed;
    private ArrayList<ImagemProxy> listaDeImagens;
    private ZeladorImagemProxy zelador;
    private TelaTodasImagens tela;
    private StateTodasImagens estado;
    private Usuario usuario;

    public PresenterTodasImagens(int qualEstado, Usuario usuario) {
        this.usuario = usuario;
        ImageFactory fabricaDeImagens = new ImageFactory();
        this.listaDeImagens = fabricaDeImagens.getImagens();
        this.zelador = new ZeladorImagemProxy();

        this.tela = new TelaTodasImagens();

        if (qualEstado == STATE_VISUALIZAR) {
            this.estado = new StateTodasImagensLista(this, this.tela);
        } else if (qualEstado == STATE_PERMISSAO) {
            this.estado = new StateTodasImagensPermissao(this, this.tela);
        } else if (qualEstado == STATE_ADMIN_PERMISSAO) {
            this.estado = new StateTodasImagensAdminPermissao(this, this.tela);
        } else {
            System.out.println("Não informado não é compativel");
            JOptionPane.showMessageDialog(tela, "O estado da tela de Visualização de imagens não foi informado");
        }
    }

    public ArrayList<JButtonImage> getListButtonImage(ArrayList<ImagemProxy> listaImagens) {
        ArrayList<JButtonImage> listBtImagem = new ArrayList<>();
        for (ImagemProxy imgProxy : listaImagens) {
            JButtonImage bt = new JButtonImage(imgProxy);
            listBtImagem.add(bt);
        }
        return listBtImagem;
    }

    public void removeListnersFromButton(JButton btn) {
        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
    }

    public void redimensionaScrollBar(TelaTodasImagens tela, int totalImagens) {
        int qtdLinhas = totalImagens / 4;
        double frac = (totalImagens / 4.0) - qtdLinhas;
        qtdLinhas = (frac > 0) ? qtdLinhas + 1 : qtdLinhas;
        tela.getPanel().setPreferredSize(new Dimension(300, qtdLinhas * 105));

    }

    public void atualizaTela() {
        redimensionaScrollBar(tela, listaDeImagens.size());
        tela.getPanel().updateUI();
        tela.getPanel().repaint();
        tela.repaint();
    }

    public void setState(StateTodasImagens estado) {
        this.estado = estado;
    }

    public JButtonImage getLastButtonPressed() {
        return lastButtonPressed;
    }

    public void setLastButtonPressed(JButtonImage lastButtonPressed) {
        this.lastButtonPressed = lastButtonPressed;
    }

    public ArrayList<ImagemProxy> getListaDeImagens() {
        return listaDeImagens;
    }

    public void setListaDeImagens(ArrayList<ImagemProxy> listaDeImagens) {
        this.listaDeImagens = listaDeImagens;
    }

    public ZeladorImagemProxy getZelador() {
        return zelador;
    }

    public void setZelador(ZeladorImagemProxy zelador) {
        this.zelador = zelador;
    }

    public TelaTodasImagens getTela() {
        return tela;
    }

    public void setTela(TelaTodasImagens tela) {
        this.tela = tela;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
