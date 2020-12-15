package state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Permissoes;
import model.Usuario;
import model.Usuarios;

import presenter.PresenterManterUsuario;
import presenter.PresenterTodasImagens;

public class StateVisualizarUsuarios extends StateManterUsuario {

    private Usuarios usuarios;
    private Permissoes permissoes;

    public StateVisualizarUsuarios(PresenterManterUsuario presenter) {
        this.presenter = presenter;
        ajusteWiew();
    }

    public void ajusteWiew() {
        presenter.getView().setLocationRelativeTo(null);
        presenter.getView().setTitle("Visualizar");
        presenter.getView().getjLabel1().setVisible(false);
        presenter.getView().getjLabel2().setVisible(false);
        presenter.getView().getjLabel3().setVisible(false);
        presenter.getView().getjLabel4().setVisible(false);
        presenter.getView().getjLabel5().setVisible(false);
        presenter.getView().getjLabel6().setVisible(false);
        presenter.getView().getjTextField1().setVisible(false);
        presenter.getView().getjTextField2().setVisible(false);
        presenter.getView().getjTextField3().setVisible(false);
        presenter.getView().getjTextField4().setVisible(false);
        presenter.getView().getjTextField5().setVisible(false);
        presenter.getView().getjCheckBox1().setVisible(false);
        presenter.getView().getjButton4().setVisible(true);
        presenter.getView().getjButton3().setVisible(true);
        presenter.getView().getjPanel1().setVisible(true);
        presenter.getView().getjTable2().setVisible(true);
        presenter.getView().setSize(600, 500);
        presenter.removeListners(presenter.getView().getjButton4());
        presenter.getView().getjButton3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                concederPermissao();
            }
        });

        presenter.removeListners(presenter.getView().getjButton4());
        presenter.getView().getjButton4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                excluirUsuario();
            }
        });

        presenter.removeListners(presenter.getView().getjButton1());
        presenter.getView().getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cadastrarUsuario();
            }
        });

        presenter.removeListners(presenter.getView().getjButton2());
        presenter.getView().getjButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelar();
            }
        });

        CarregarDados();

    }

    public void CarregarDados() {
        DefaultTableModel tableModel = new DefaultTableModel();
        presenter.getView().getjTable2().setModel(tableModel);
        usuarios = Usuarios.getInstance();

        tableModel.addColumn("NOME");
        tableModel.addColumn("E-MAIL");
        tableModel.addColumn("TELEFONE");
        tableModel.addColumn("LOGIN");
        tableModel.addColumn("ADMINISTRADOR");

        ArrayList<Usuario> array = usuarios.buscarTodosUsuario();

        for (Usuario usuario : array) {
            tableModel.addRow(new Object[]{usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getLogin(), usuario.isAdministrador()});
        }
        atualizaTela();

    }

    public void atualizaTela() {
        presenter.getView().getjPanel1().updateUI();
        presenter.getView().getjPanel1().repaint();
        presenter.getView().repaint();
    }

    @Override
    public void cadastrarUsuario() {
        presenter.setEstado(new StateCadastroUsuario(this.presenter));
    }

    @Override
    public void excluirUsuario() {
        usuarios = Usuarios.getInstance();
        permissoes = Permissoes.getInstance();
        if (usuarios.getUsuarioLogado().isAdministrador()) {
            int row = presenter.getView().getjTable2().getSelectedRow();
            if (row >= 0) {
                String nome = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 0);
                String email = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 1);
                String telefone = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 2);
                String login = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 3);
                boolean tipo = (boolean) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 4);
                Usuario u = new Usuario(nome, email, telefone, login, "", tipo);
                permissoes.removerAllPermissoes(u);
                usuarios.excluirUsuarios(u);
                CarregarDados();
                presenter.setEstado(new StateVisualizarUsuarios(this.presenter));
            }
        } else {
            StringBuilder sb = new StringBuilder("ATIVIDADE NAO PODE SER EXECUTADA, CONTATE UM ADMINISTRADOR");
            JOptionPane.showMessageDialog(presenter.getView(), sb.toString());
        }
    }

    @Override
    public void editarUsuario() {
        System.out.println("Operação Inválida");
    }

    @Override
    public void cancelar() {
        presenter.getView().dispose();
    }

    @Override
    public void concederPermissao() {
        int row = presenter.getView().getjTable2().getSelectedRow();
        if (row >= 0) {
            String nome = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 0);
            String email = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 1);
            String telefone = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 2);
            String login = (String) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 3);
            boolean tipo = (boolean) presenter.getView().getjTable2().getModel().getValueAt(presenter.getView().getjTable2().getSelectedRow(), 4);
            Usuario u = new Usuario(nome, email, telefone, login, "", tipo);
            if (tipo) {
                PresenterTodasImagens presenter = new PresenterTodasImagens(PresenterTodasImagens.STATE_ADMIN_PERMISSAO, u);
            } else {
                PresenterTodasImagens presenterTodasImagens = new PresenterTodasImagens(PresenterTodasImagens.STATE_PERMISSAO, u);
            }
        }
        CarregarDados();
        presenter.setEstado(new StateVisualizarUsuarios(this.presenter));
    }

}
