package proxy_dao;

import java.sql.SQLException;
import java.sql.*;
import java.util.HashMap;

public abstract class DAOImagem {

    protected Connection conn;
    protected Statement stm;

    public DAOImagem() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        this.conn = DriverManager.getConnection("jdbc:sqlite:src\\SQLite.db");
        this.stm = this.conn.createStatement();

    }

    public abstract void insertImagem(Imagem imagem);

    public abstract void deleteImagem(ImagemProxy imagemProxy);

    public abstract HashMap<String, String> getImagens();

    public abstract ImagemProxy getImagem(String nome);

}
