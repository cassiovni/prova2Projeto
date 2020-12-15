package principal;

import proxy_dao.SQLiteUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import presenter.PresenterManterUsuario;
import presenter.PresenterLogin;

public class Principal {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (SQLiteUsuario.getInstance().quantidadePessoa() == 0) {
            PresenterManterUsuario manter = new PresenterManterUsuario();
        } else {
            new PresenterLogin().inicial();
        }

    }

}
