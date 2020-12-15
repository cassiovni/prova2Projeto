package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuarios;

import view.ViewLogin;

public class PresenterLogin {

    public void inicial() {
        ViewLogin view = new ViewLogin();
        Usuarios usuarios = Usuarios.getInstance();

        view.setVisible(true);

        view.getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String login = view.getjTextField1().getText();
                String senha = view.getjTextField2().getText();

                if (usuarios.autenticarSessao(login, senha)) {
                    view.dispose();
                    PresenterTelaPrincipal p = new PresenterTelaPrincipal();
                    p.setUserSessao();
                    p.inicial();
                } else {
                    StringBuilder sb = new StringBuilder("CREDENCIAIS INVALIDAS");
                    JOptionPane.showMessageDialog(view, sb.toString());
                }
            }
        });

        view.getjButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                view.dispose();
            }
        });

        view.setLocationRelativeTo(null);
    }

}
