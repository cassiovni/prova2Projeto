package state;

import proxy_dao.ImagemProxy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Imagens;
import model.Notificacao;
import model.Notificacoes;
import model.Usuario;
import model.Usuarios;
import presenter.PresenterImagemExibicao;
import presenter.PresenterNotificacao;
import view.TelaNotificacoes;

public class StateNotificacoesAdmin extends StateNotificacoes {

    private TelaNotificacoes tela;
    private PresenterNotificacao presenterNotificacao;
    private DefaultTableModel tm;
    ArrayList<Usuario> listaUsuariosSemAcesso;

    public StateNotificacoesAdmin(PresenterNotificacao presenterNotificacao, TelaNotificacoes tela) {
        super(presenterNotificacao, tela);
        this.tela = tela;
        this.presenterNotificacao = presenterNotificacao;
        configurarTela();
    }

    public void configurarTela() {

        carregaNotiticacoesNaTabela();

        tela.getBtnOpcao1().setVisible(true);
        tela.getBtnOpcao2().setVisible(true);
        tela.getBtnOpcao3().setVisible(true);

        tela.getBtnOpcao2().setText("Autorizar");
        tela.getBtnOpcao1().setText("Negar");
        tela.getBtnOpcao3().setText("Visualizar imagem");

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao2());
        tela.getBtnOpcao2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    autorizarNotificacao();
                } else {
                    JOptionPane.showMessageDialog(tela, "Você deve selecionar uma notificação");
                }
            }
        });

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao1());
        tela.getBtnOpcao1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    negarNotificacao();
                } else {
                    JOptionPane.showMessageDialog(tela, "Você deve selecionar uma notificação");
                }
            }
        });

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao3());
        tela.getBtnOpcao3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    exibeImagem();
                } else {
                    JOptionPane.showMessageDialog(tela, "Você deve selecionar uma notificação");
                }
            }
        });

        tela.setTitle("Selecione os usuários que ainda não tem acesso para compartilhar a imagem");
    }

    public void exibeImagem() {
        int row = tela.getTableDados().getSelectedRow();

        if (row >= 0) {
            String nomeImagem = (String) tm.getValueAt(row, 2);
            System.out.println("Nome da imagem: " + nomeImagem);
            ImagemProxy img = Imagens.getInstance().getImagemNome(nomeImagem);
            PresenterImagemExibicao presenterImagemExibicao = new PresenterImagemExibicao(tela, nomeImagem, img.getDiretorio(Usuarios.getInstance().getUsuarioLogado()));
        }
    }

    public void negarNotificacao() {
        int row = tela.getTableDados().getSelectedRow();

        if (row >= 0) {
            String nomeUsuario = (String) tm.getValueAt(row, 1);
            String nomeImagem = (String) tm.getValueAt(row, 2);
            String tipo = (String) tm.getValueAt(row, 3);
            String status = (String) tm.getValueAt(row, 4);

            Usuario usuario = Usuarios.getInstance().getUsuario(nomeUsuario);
            ImagemProxy img = Imagens.getInstance().getImagemNome(nomeImagem);

            Notificacao notificacao = new Notificacao(tipo, usuario, img, status);
            Notificacoes.getInstance().removeNotificacao(notificacao);

            carregaNotiticacoesNaTabela();
        }
    }

    public void autorizarNotificacao() {
        int row = tela.getTableDados().getSelectedRow();

        if (row >= 0) {
            String nomeUsuario = (String) tm.getValueAt(row, 1);
            String nomeImagem = (String) tm.getValueAt(row, 2);
            String tipo = (String) tm.getValueAt(row, 3);
            String status = (String) tm.getValueAt(row, 4);

            Usuario usuario = Usuarios.getInstance().getUsuario(nomeUsuario);
            ImagemProxy img = Imagens.getInstance().getImagemNome(nomeImagem);

            Notificacao notificacao = new Notificacao(tipo, usuario, img, status);

            ImagemProxy imgProxy = Imagens.getInstance().getImagemNome(nomeImagem);
            boolean inseriu = imgProxy.setPermissao(Usuarios.getInstance().getUsuarioLogado(), usuario, true);

            if (inseriu) {
                Notificacoes.getInstance().removeNotificacao(notificacao);
                notificacao.setStatus(Notificacao.STATUS_CONCLUIDA);
                Notificacoes.getInstance().addNotificacao(notificacao);
            }

            carregaNotiticacoesNaTabela();
        }
    }

    private void carregaNotiticacoesNaTabela() {
        ArrayList<Notificacao> notificacoes = Notificacoes.getInstance().getNotificacoes();

        Object colunas[] = {"Descrição", "Usuario", "Imagem", "Tipo", "Status"};
        tm = new DefaultTableModel(colunas, 0);
        tela.getTableDados().setModel(tm);
        tm.setNumRows(0);

        for (int i = notificacoes.size() - 1; i >= 0; i--) {
            Notificacao n = notificacoes.get(i);
            String img = n.getImg().getNome(Usuarios.getInstance().getUsuarioLogado());
            Usuario para = n.getPara();
            String tipo = n.getTipo();
            String status = n.getStatus();
            String descricao = "";

            if (tipo.equals(Notificacao.TIPO_COMPARTILHAMENTO)) {
                descricao = "Solicitação de compatilhamento da imagem " + img + " para o usuário " + para.getNome();
            } else {
                descricao = "O usuário " + para.getNome() + " deseja acessar a imagem " + img;
            }

            if (status.equals(Notificacao.STATUS_NOVA)) {
                tm.addRow(new Object[]{descricao, para.getNome(), img, tipo, status});
            }
        }

        tela.getTableDados().getColumnModel().getColumn(1).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(1).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(2).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(2).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(3).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(3).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(4).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(4).setMaxWidth(0);
    }

}
