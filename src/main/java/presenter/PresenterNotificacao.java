package presenter;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import state.StateNotificacoes;
import state.StateNotificacoesSelecionaUsuariosUsuario;
import proxy_dao.ImagemProxy;
import model.Usuarios;
import state.StateNotificacoesAdmin;
import state.StateNotificacoesSelecionaUsuariosAdmin;
import state.StateNotificacoesUsuario;

import view.TelaNotificacoes;

public final class PresenterNotificacao {

    private DefaultTableModel tm;
    private TelaNotificacoes tela;
    private ImagemProxy imagem;
    private JFrame telaAnterior;
    private StateNotificacoes estado;

    public PresenterNotificacao(JFrame telaAnterior, ImagemProxy img) {
        this.telaAnterior = telaAnterior;
        this.imagem = img;
        this.tela = new TelaNotificacoes();

        if (img != null) {
            if (Usuarios.getInstance().getUsuarioLogado().isAdministrador()) {
                this.estado = new StateNotificacoesSelecionaUsuariosAdmin(this, tela);
            } else {
                this.estado = new StateNotificacoesSelecionaUsuariosUsuario(this, tela);
            }
        } else {
            if (Usuarios.getInstance().getUsuarioLogado().isAdministrador()) {
                this.estado = new StateNotificacoesAdmin(this, tela);
            } else {
                this.estado = new StateNotificacoesUsuario(this, tela);
            }
        }

        if (estado != null) {
            configurarTela();
        }
    }

    public void configurarTela() {

        telaAnterior.setVisible(false);

        tela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fecharTela();
            }
        });

        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }

    public void fecharTela() {
        tela.dispose();
        telaAnterior.setVisible(true);
    }

    public void removeListnersFromButton(JButton btn) {
        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
    }

    public TelaNotificacoes getTela() {
        return this.tela;
    }

    public ImagemProxy getImagem() {
        return imagem;
    }

}
