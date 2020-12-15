package state;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import model.Notificacao;
import model.Notificacoes;
import model.Usuarios;
import presenter.PresenterImagemExibicao;
import presenter.PresenterNotificacao;
import presenter.PresenterTodasImagens;
import proxy_dao.ImagemProxy;
import proxy_dao.ImagemProxyMemento;
import proxy_dao.JButtonImage;
import model.Imagens;
import model.Usuario;
import view.TelaTodasImagens;

public class StateTodasImagensLista extends StateTodasImagens {

    public StateTodasImagensLista(PresenterTodasImagens presenterTodasImagens, TelaTodasImagens view) {
        super(presenterTodasImagens, view);
        configurarTela();
    }

    public void configurarTela() {
        tela.getPanel().setLayout(new FlowLayout());
        tela.getPanel().setPreferredSize(new Dimension(300, 100));
        tela.getSchollPane().setAutoscrolls(true);
        tela.getSchollPane().setViewportView(tela.getPanel());

        tela.getBtnDesfazer().setEnabled(false);
        tela.getBtnDesfazer().setVisible(true);
        tela.getTxtField().setVisible(false);

        presenterTodasImagens.removeListnersFromButton(tela.getBtnDesfazer());
        this.tela.getBtnDesfazer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desfazerExcluir();
            }
        });

        ArrayList<JButtonImage> listaButtonImage = presenterTodasImagens.getListButtonImage(presenterTodasImagens.getListaDeImagens());
        for (JButtonImage bt : listaButtonImage) {

            bt = configurarBotaoDeImagem(bt);

            tela.getPanel().add(bt);
        }

        presenterTodasImagens.getTela().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                e.getWindow().dispose();
                persistirAlteracaoes();
            }

        });

        atualizaTela();
        tela.setTitle("Lista de imagens");
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }

    public void persistirAlteracaoes() {
        while (presenterTodasImagens.getZelador().containsEstados()) {
            ImagemProxyMemento imgProxyMemento = presenterTodasImagens.getZelador().getUltimoEstado();
            ImagemProxy imgProxy = new ImagemProxy(null);
            imgProxy.restaura(imgProxyMemento);
            Imagens.getInstance().deleteImagem(imgProxy);
        }
    }

    public void solicitarAcesso() {
        String tipo = Notificacao.TIPO_ACESSO;
        Usuario usuario = Usuarios.getInstance().getUsuarioLogado();
        ImagemProxy img = presenterTodasImagens.getLastButtonPressed().getImagemProxy();
        String status = Notificacao.STATUS_NOVA;
        Notificacao n = new Notificacao(tipo, usuario, img, status);
        Notificacoes.getInstance().addNotificacao(n);
    }

    public JButtonImage configurarBotaoDeImagem(JButtonImage bt) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem opcaoAbrir = getMenuItemAbrir(bt);
        JMenuItem opcaoExcluir = getMenuItemExcluir(bt);
        JMenuItem opcaoCompartilhar = getMenuItemCompartilhar(bt);

        popup.add(opcaoAbrir);
        popup.add(opcaoExcluir);
        popup.add(opcaoCompartilhar);

        bt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
                presenterTodasImagens.setLastButtonPressed(bt);
            }
        });

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

    public JMenuItem getMenuItemAbrir(JButtonImage bt) {
        JMenuItem opcaoAbrir = new JMenuItem("Abrir");
        opcaoAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                if (!bt.getImagemProxy().isAuthorized(presenterTodasImagens.getUsuario())) {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Você não tem acesso a esta imagem. Deseja solicitar?", "Acesso não autorizado", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        solicitarAcesso();
                        JOptionPane.showMessageDialog(tela, "Solicitação de acesso enviada");
                    }
                } else {
                    ImagemProxy imgProxy = presenterTodasImagens.getLastButtonPressed().getImagemProxy();
                    String nomeImagem = imgProxy.getNome(presenterTodasImagens.getUsuario());
                    String diretorioImagem = imgProxy.getDiretorio(presenterTodasImagens.getUsuario());

                    PresenterImagemExibicao presenterImagemExibicao = new PresenterImagemExibicao(tela, nomeImagem, diretorioImagem);
                    tela.setVisible(false);
                }
            }
        });
        return opcaoAbrir;
    }

    public JMenuItem getMenuItemExcluir(JButtonImage bt) {
        JMenuItem opcaoExcluir = new JMenuItem("Excluir");
        opcaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (!bt.getImagemProxy().isAuthorized(presenterTodasImagens.getUsuario())) {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Você não tem acesso a esta imagem. Deseja solicitar?", "Acesso não autorizado", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        solicitarAcesso();
                        JOptionPane.showMessageDialog(tela, "Solicitação de acesso enviada");
                    }
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja exluir?", "Excluir imagem", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        excluirImagem();
                        atualizaTela();
                    }
                }
            }
        });
        return opcaoExcluir;
    }

    public JMenuItem getMenuItemCompartilhar(JButtonImage bt) {
        JMenuItem opcaoCompartilhar = new JMenuItem("Compartilhar");
        opcaoCompartilhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (!bt.getImagemProxy().isAuthorized(presenterTodasImagens.getUsuario())) {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Você não tem acesso a esta imagem. Deseja solicitar?", "Acesso não autorizado", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        solicitarAcesso();
                        JOptionPane.showMessageDialog(tela, "Solicitação de acesso enviada");
                    }
                } else {
                    JButtonImage bt = presenterTodasImagens.getLastButtonPressed();
                    ImagemProxy imgProxy = bt.getImagemProxy();
                    PresenterNotificacao presenterNotificacao = new PresenterNotificacao(tela, imgProxy);
                    tela.setVisible(false);
                }
            }
        });

        return opcaoCompartilhar;
    }

    public void desfazerExcluir() {
        ImagemProxyMemento imgProxyMemento = presenterTodasImagens.getZelador().getUltimoEstado();
        ImagemProxy imgProxy = new ImagemProxy(null);
        imgProxy.restaura(imgProxyMemento);
        presenterTodasImagens.getListaDeImagens().add(imgProxy);
        JButtonImage bt = new JButtonImage(imgProxy);
        bt = configurarBotaoDeImagem(bt);
        tela.getPanel().add(bt);
        atualizaTela();
    }

    public void excluirImagem() {
        ImagemProxy imgProxy = presenterTodasImagens.getLastButtonPressed().getImagemProxy();
        ImagemProxyMemento imgProMemento = imgProxy.getImagemProxyMemento();
        presenterTodasImagens.getZelador().addPessoaMemento(imgProMemento);
        presenterTodasImagens.getListaDeImagens().remove(imgProxy);
        excluirImagemDaTela(presenterTodasImagens.getLastButtonPressed());
    }

    public void excluirImagemDaTela(JButtonImage botao) {
        Component[] componentes = tela.getPanel().getComponents();
        for (Component c : componentes) {
            if (c instanceof JButtonImage) {
                JButtonImage bt = (JButtonImage) c;
                if (bt == presenterTodasImagens.getLastButtonPressed()) {
                    tela.getPanel().remove(c);
                }
            }
        }
    }

    public void atualizaTela() {
        if (presenterTodasImagens.getZelador().containsEstados()) {
            this.tela.getBtnDesfazer().setEnabled(true);
        } else {
            tela.getBtnDesfazer().setEnabled(false);
        }
        presenterTodasImagens.atualizaTela();
    }

}
