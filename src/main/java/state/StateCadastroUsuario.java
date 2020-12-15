package state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Permissoes;
import model.Usuario;
import model.Usuarios;

import presenter.PresenterManterUsuario;

public class StateCadastroUsuario extends StateManterUsuario {

    Usuarios usuarios;
    Permissoes permissoes;

    public StateCadastroUsuario(PresenterManterUsuario presenter) {
        this.presenter = presenter;
        ajusteWiew();
    }

    public void ajusteWiew() {
        presenter.getView().setLocationRelativeTo(null);

        presenter.getView().setTitle("Cadastro");
        presenter.getView().getjButton1().setText("CADASTRAR");
        presenter.getView().getjLabel1().setVisible(true);
        presenter.getView().getjLabel2().setVisible(true);
        presenter.getView().getjLabel3().setVisible(true);
        presenter.getView().getjLabel4().setVisible(true);
        presenter.getView().getjLabel5().setVisible(true);
        presenter.getView().getjLabel6().setVisible(true);
        presenter.getView().getjTextField1().setVisible(true);
        presenter.getView().getjTextField1().setText("");
        presenter.getView().getjTextField2().setVisible(true);
        presenter.getView().getjTextField2().setText("");
        presenter.getView().getjTextField3().setVisible(true);
        presenter.getView().getjTextField3().setText("");
        presenter.getView().getjTextField4().setVisible(true);
        presenter.getView().getjTextField4().setText("");
        presenter.getView().getjTextField5().setVisible(true);
        presenter.getView().getjTextField5().setText("");
        presenter.getView().getjCheckBox1().setVisible(true);
        presenter.getView().getjCheckBox1().setSelected(false);
        presenter.getView().getjCheckBox1().setEnabled(true);
        presenter.getView().getjButton3().setVisible(false);
        presenter.getView().getjButton4().setVisible(false);
        presenter.getView().getjTable2().setVisible(false);
        presenter.getView().getjPanel1().setVisible(false);
        presenter.getView().setSize(600, 500);

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

    }

    @Override
    public void cadastrarUsuario() {
        Usuario pessoa;
        pessoa = new Usuario(presenter.getView().getjTextField1().getText(), presenter.getView().getjTextField2().getText(), presenter.getView().getjTextField3().getText(),
                presenter.getView().getjTextField4().getText(), presenter.getView().getjTextField5().getText(), presenter.getView().getjCheckBox1().isSelected());
        usuarios = Usuarios.getInstance();
        usuarios.cadastrarUsuarios(pessoa);
        permissoes = Permissoes.getInstance();
        System.out.println("DEBUGANDO AQUI");
        permissoes.insertPermissaoAdministrador(pessoa);
        presenter.setEstado(new StateVisualizarUsuarios(this.presenter));

    }

    @Override
    public void excluirUsuario() {
        System.out.println("Operação Inválida");
    }

    @Override
    public void editarUsuario() {
        System.out.println("Operação Inválida");
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new StateVisualizarUsuarios(this.presenter));
    }

    @Override
    public void CarregarDados() {
        System.out.println("Operação Inválida");
    }

    @Override
    public void concederPermissao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
