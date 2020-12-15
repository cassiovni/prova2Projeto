package proxy_dao;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import model.Usuario;

public abstract class DAOUsuario {

    protected Connection conn;
    protected Statement stm;

    public DAOUsuario() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        this.conn = DriverManager.getConnection("jdbc:sqlite:src\\SQLite.db");
        this.stm = this.conn.createStatement();

    }

    public abstract void insertPessoa(Usuario pessoa);

    public abstract void deletePessoa(Usuario pessoa);

    public abstract int quantidadePessoa();

    public abstract ArrayList<Usuario> getAllUsers();

    public abstract boolean getUsuario(String nome, String password);

    public abstract Usuario getUser(String nome, String password);

}
