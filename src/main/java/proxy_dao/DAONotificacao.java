package proxy_dao;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import model.Notificacao;
import model.Usuario;

public abstract class DAONotificacao {

    protected Connection conn;
    protected Statement stm;

    public DAONotificacao() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        this.conn = DriverManager.getConnection("jdbc:sqlite:src\\SQLite.db");
        this.stm = this.conn.createStatement();

    }

    public abstract void insertNotificacao(Notificacao notificacao);

    public abstract void removeNotificacao(Notificacao notificacao);

    public abstract ArrayList<Notificacao> getAllNotificacao();

    public abstract boolean existeNotificacaStatuso(Usuario usuario, ImagemProxy imagemProxy, String status);

    public abstract boolean existeNotificacao(Usuario usuario, ImagemProxy imagemProxy);

}
