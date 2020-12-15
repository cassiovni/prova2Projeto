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
import model.Usuarios;
import presenter.PresenterImagemExibicao;
import presenter.PresenterNotificacao;
import view.TelaNotificacoes;

public class StateNotificacoesUsuario extends StateNotificacoes {

    private TelaNotificacoes tela;
    private PresenterNotificacao presenterNotificacao;
    private DefaultTableModel tm;

    public StateNotificacoesUsuario(PresenterNotificacao presenterNotificacao, TelaNotificacoes tela) {
        super(presenterNotificacao, tela);
        this.tela = tela;
        this.presenterNotificacao = presenterNotificacao;
        configurarTela();
    }

    public void configurarTela() {

        carregaNotiticacoesNaTabela();

        tela.getBtnOpcao1().setVisible(false);
        tela.getBtnOpcao2().setVisible(true);
        tela.getBtnOpcao3().setVisible(false);
        tela.getBtnOpcao2().setText("Visualizar");

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao2());
        tela.getBtnOpcao2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    exibeImagem();
                }
            }
        });

        tela.setTitle("Selecione os usuários que ainda não tem acesso para compartilhar a imagem");
    }

    public void exibeImagem() {
        int row = tela.getTableDados().getSelectedRow();

        if (row >= 0) {
            String nomeImagem = (String) tm.getValueAt(row, 1);
            System.out.println("Nome da imagem: " + nomeImagem);
            ImagemProxy img = Imagens.getInstance().getImagemNome(nomeImagem);

            if (img.isAuthorized(Usuarios.getInstance().getUsuarioLogado())) {
                PresenterImagemExibicao presenterImagemExibicao = new PresenterImagemExibicao(tela, nomeImagem, img.getDiretorio(Usuarios.getInstance().getUsuarioLogado()));
            } else {
                JOptionPane.showMessageDialog(tela, "Você não tem mais acesso a esta imagem. Solicite novamente ao administrador.");
            }

        }
    }

    private void carregaNotiticacoesNaTabela() {
        ArrayList<Notificacao> notificacoes = Notificacoes.getInstance().getNotificacoesDoUsuarioConcluidas(Usuarios.getInstance().getUsuarioLogado());

        Object colunas[] = {"Descrição", "Imagem"};
        tm = new DefaultTableModel(colunas, 0);
        tela.getTableDados().setModel(tm);
        tm.setNumRows(0);

        for (Notificacao n : notificacoes) {
            String nomeImagem = n.getNomeImagem();
            String tipo = n.getTipo();
            String descricao = "";
            if (tipo.equals(Notificacao.TIPO_COMPARTILHAMENTO)) {
                descricao = "A imagem " + nomeImagem + " foi compartilhada com você";
            } else {
                descricao = "Sua solicitação de acesso a imagem " + nomeImagem + "foi aprovada";
            }
            tm.addRow(new Object[]{descricao, nomeImagem});
        }

        tela.getTableDados().getColumnModel().getColumn(1).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(1).setMaxWidth(0);
    }

}
