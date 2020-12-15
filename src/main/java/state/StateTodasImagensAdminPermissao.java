package state;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import presenter.PresenterTodasImagens;
import proxy_dao.JButtonImage;
import view.TelaTodasImagens;

public class StateTodasImagensAdminPermissao extends StateTodasImagens {

    public StateTodasImagensAdminPermissao(PresenterTodasImagens presenterTodasImagens, TelaTodasImagens view) {
        super(presenterTodasImagens, view);
        configurarTela();
    }

    public void configurarTela() {
        tela.getPanel().setLayout(new FlowLayout());
        tela.getPanel().setPreferredSize(new Dimension(300, 100));
        tela.getSchollPane().setAutoscrolls(true);
        tela.getSchollPane().setViewportView(tela.getPanel());

        tela.getBtnDesfazer().setEnabled(false);
        tela.getBtnDesfazer().setVisible(false);

        tela.getTxtField().setVisible(true);
        tela.getTxtField().setEnabled(false);
        tela.getTxtField().setText(presenterTodasImagens.getUsuario().getNome());

        ArrayList<JButtonImage> listaButtonImage = presenterTodasImagens.getListButtonImage(presenterTodasImagens.getListaDeImagens());
        for (JButtonImage bt : listaButtonImage) {
            bt = configurarBotaoDeImagem(bt);
            tela.getPanel().add(bt);
        }

        atualizaTela();
        tela.setTitle("Permissão de imagens");
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }

    public JButtonImage configurarBotaoDeImagem(JButtonImage bt) {

        bt.setForeground(Color.black);
        bt.setBorderPainted(true);
        bt.setFont(new java.awt.Font("Arial", 1, 8));
        bt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        if (bt.getImagemProxy().isAuthorized(presenterTodasImagens.getUsuario())) {
            bt.setText("Autorizado");
            bt.setForeground(Color.blue);
        } else {
            bt.setText("Não autorizado");
            bt.setForeground(Color.red);
        }
        return bt;
    }

    public void atualizaTela() {
        presenterTodasImagens.atualizaTela();
    }

}
