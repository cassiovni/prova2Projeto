package presenter;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import model.Usuarios;
import state.StatePrimeiroAcessoUsuario;
import state.StateManterUsuario;
import state.StateVisualizarUsuarios;
import view.ViewManterUsuario;

public class PresenterManterUsuario {

    private ViewManterUsuario view;
    StateManterUsuario estado;
    Usuarios usuarios;

    public PresenterManterUsuario() {

        usuarios = Usuarios.getInstance();
        this.view = new ViewManterUsuario();
        if (usuarios.quantidadeUsuarios() == 0) {
            this.estado = new StatePrimeiroAcessoUsuario(this);
        } else {
            this.estado = new StateVisualizarUsuarios(this);
        }
        view.setVisible(true);
    }

    public void removeListners(JButton btn) {
        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
    }

    public void change() {
        this.estado = new StateVisualizarUsuarios(this);
    }

    public void setEstado(StateManterUsuario estado) {
        this.estado = estado;
    }

    public ViewManterUsuario getView() {
        return view;
    }

}
