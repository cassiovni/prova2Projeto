package state;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import model.Usuario;
import model.Usuarios;
import presenter.PresenterTodasImagens;
import proxy_dao.JButtonImage;
import view.TelaTodasImagens;

public class StateTodasImagensPermissao extends StateTodasImagens {

    public StateTodasImagensPermissao(PresenterTodasImagens presenterTodasImagens, TelaTodasImagens view) {
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

        JPopupMenu popup = new JPopupMenu();
        JMenuItem opcaoAcessar = getMenuItemAcesso(bt);
        popup.add(opcaoAcessar);

        bt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                presenterTodasImagens.setLastButtonPressed(bt);

                if (bt.getText().equals("Autorizado")) {
                    opcaoAcessar.setText("Remover acesso");
                } else {
                    opcaoAcessar.setText("Autorizar");
                }
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        return bt;
    }

    public JMenuItem getMenuItemAcesso(JButtonImage bt) {
        JMenuItem opcaoAcessar = new JMenuItem("Acesso");
        opcaoAcessar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Usuario usuarioAdmin = Usuarios.getInstance().getUsuarioLogado();
                Usuario usuarioParaAlterar = presenterTodasImagens.getUsuario();

                if (presenterTodasImagens.getLastButtonPressed().getText().equals("Autorizado")) {
                    presenterTodasImagens.getLastButtonPressed().setText("Não autorizado");
                    presenterTodasImagens.getLastButtonPressed().setForeground(Color.red);
                    bt.getImagemProxy().setPermissao(usuarioAdmin, usuarioParaAlterar, false);
                } else {
                    presenterTodasImagens.getLastButtonPressed().setText("Autorizado");
                    presenterTodasImagens.getLastButtonPressed().setForeground(Color.blue);
                    bt.getImagemProxy().setPermissao(usuarioAdmin, usuarioParaAlterar, true);
                }
                presenterTodasImagens.getLastButtonPressed().updateUI();
                presenterTodasImagens.getLastButtonPressed().repaint();
                atualizaTela();
            }
        });

        return opcaoAcessar;
    }

    public void atualizaTela() {
        presenterTodasImagens.atualizaTela();
    }

}
