package state;

import proxy_dao.ImagemProxy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Notificacao;
import model.Notificacoes;
import model.Permissoes;
import model.Usuario;
import model.Usuarios;
import presenter.PresenterNotificacao;
import view.TelaNotificacoes;

public class StateNotificacoesSelecionaUsuariosAdmin extends StateNotificacoes {

    private TelaNotificacoes tela;
    private PresenterNotificacao presenterNotificacao;
    private ImagemProxy imagem;
    private DefaultTableModel tm;
    ArrayList<Usuario> listaUsuariosSemAcesso;

    public StateNotificacoesSelecionaUsuariosAdmin(PresenterNotificacao presenterNotificacao, TelaNotificacoes tela) {
        super(presenterNotificacao, tela);
        this.imagem = presenterNotificacao.getImagem();
        this.tela = tela;
        this.presenterNotificacao = presenterNotificacao;
        listaUsuariosSemAcesso = Permissoes.getInstance().getUsuariosSemAcesso(imagem);
        configurarTela();
    }

    public void configurarTela() {

        Object colunas[] = {"Nome", "E-mail", "Telefone", "Login", "Administrador"};

        tm = new DefaultTableModel(colunas, 0);
        carregaUsuariosNaTabela(listaUsuariosSemAcesso);
        tela.getTableDados().setModel(tm);

        tela.getBtnOpcao1().setVisible(false);
        tela.getBtnOpcao2().setVisible(true);
        tela.getBtnOpcao3().setVisible(false);
        tela.getBtnOpcao2().setText("Autorizar");

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao2());
        tela.getBtnOpcao2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja autorizar o acesso a imagem?", "Compatilhamento de imagem", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        autorizarAcesso();
                        JOptionPane.showMessageDialog(tela, "Autorização concedida");
                        presenterNotificacao.fecharTela();
                    }
                }
            }
        });

        tela.getTableDados().getColumnModel().getColumn(1).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(1).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(2).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(2).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(3).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(3).setMaxWidth(0);
        tela.getTableDados().getColumnModel().getColumn(4).setMinWidth(0);
        tela.getTableDados().getColumnModel().getColumn(4).setMaxWidth(0);

        tela.setTitle("Selecione os usuários para dar acesso");
    }

    public void autorizarAcesso() {
        int row = tela.getTableDados().getSelectedRow();

        if (row >= 0) {
            String nomeUsuario = (String) tm.getValueAt(row, 1);
            Usuario usuario = Usuarios.getInstance().getUsuario(nomeUsuario);

            Notificacao notificacao = new Notificacao(Notificacao.TIPO_COMPARTILHAMENTO, usuario, imagem, Notificacao.STATUS_CONCLUIDA);
            boolean inseriu = imagem.setPermissao(Usuarios.getInstance().getUsuarioLogado(), usuario, true);

            if (inseriu) {
                Notificacoes.getInstance().addNotificacao(notificacao);
            }
        }
    }

    private void carregaUsuariosNaTabela(ArrayList<Usuario> usuarios) {
        tm.setNumRows(0);

        for (Usuario u : usuarios) {
            String nome = u.getNome();
            String email = u.getEmail();
            String telefone = u.getTelefone();
            String login = u.getLogin();
            boolean tipo = u.isAdministrador();

            tm.addRow(new Object[]{nome, email, telefone, login, tipo});
        }
    }

}
