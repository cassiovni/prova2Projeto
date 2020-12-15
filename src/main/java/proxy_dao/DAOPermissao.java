package proxy_dao;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import model.Permissao;
import model.Usuario;

public abstract class DAOPermissao {

    protected Connection conn;
    protected Statement stm;

    public DAOPermissao() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        this.conn = DriverManager.getConnection("jdbc:sqlite:src\\SQLite.db");
        this.stm = this.conn.createStatement();

    }

    public abstract void insertPermissao(Permissao permissao);

    public abstract void deletePermissao(Permissao permissao);

    public abstract boolean estaAutorizado(Permissao permissao);

    public abstract void insertPermissaoAdministrador(Usuario pessoa);

    public abstract void removerAllPermissoes(Usuario pessoa);

    public abstract ArrayList<Usuario> getUsuariosSemAcesso(ImagemProxy img);

    public abstract void removerPermissoesImagem(ImagemProxy imagemProxy);

}
