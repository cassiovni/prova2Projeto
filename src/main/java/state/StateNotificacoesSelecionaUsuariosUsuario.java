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
import presenter.PresenterNotificacao;
import view.TelaNotificacoes;

public class StateNotificacoesSelecionaUsuariosUsuario extends StateNotificacoes {

    private TelaNotificacoes tela;
    private PresenterNotificacao presenterNotificacao;
    private ImagemProxy imagem;
    private DefaultTableModel tm;
    ArrayList<Usuario> listaUsuariosSemAcesso;

    public StateNotificacoesSelecionaUsuariosUsuario(PresenterNotificacao presenterNotificacao, TelaNotificacoes tela) {
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

        //Tela
        tela.getBtnOpcao1().setVisible(false);
        tela.getBtnOpcao2().setVisible(true);
        tela.getBtnOpcao3().setVisible(false);
        tela.getBtnOpcao2().setText("Enviar");

        presenterNotificacao.removeListnersFromButton(tela.getBtnOpcao2());
        tela.getBtnOpcao2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tela.getTableDados().getSelectedRow();
                if (row >= 0) {
                    int dialogResult = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja solicitar o compatilhamento da imagem?", "Compatilhamento de imagem", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        enviarSolicitacaoDeCompartilhamento();
                        JOptionPane.showMessageDialog(tela, "Solicitação de acesso enviada");
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
        tela.setTitle("Selecione os usuários que ainda não tem acesso para compartilhar a imagem");
    }

    public void enviarSolicitacaoDeCompartilhamento() {

        int[] linhas = tela.getTableDados().getSelectedRows();

        ArrayList<Usuario> usuariosSelecionados = new ArrayList<>();
        for (int linha : linhas) {
            String nome = (String) tm.getValueAt(linha, 0);
            String email = (String) tm.getValueAt(linha, 1);
            String telefone = (String) tm.getValueAt(linha, 2);
            String login = (String) tm.getValueAt(linha, 3);
            boolean tipo = (Boolean) tm.getValueAt(linha, 4);
            usuariosSelecionados.add(new Usuario(nome, email, telefone, login, null, tipo));
        }

        for (Usuario usuario : usuariosSelecionados) {
            Notificacao n = new Notificacao(Notificacao.TIPO_COMPARTILHAMENTO, usuario, imagem, Notificacao.STATUS_NOVA);
            Notificacoes.getInstance().addNotificacao(n);
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

            if (!existeNotificacaoNova(u, imagem)) {
                tm.addRow(new Object[]{nome, email, telefone, login, tipo});
            }

        }
    }

    public boolean existeNotificacaoNova(Usuario usuario, ImagemProxy imagem) {
        return Notificacoes.getInstance().existeNotificacaoWithStatus(usuario, imagem, Notificacao.STATUS_NOVA);
    }

}
