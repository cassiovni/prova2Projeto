package proxy_dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

public class SQLiteUsuario extends DAOUsuario {

    private static SQLiteUsuario _unicaInstancia;

    public SQLiteUsuario() throws SQLException, ClassNotFoundException {
        super();
    }

    public static SQLiteUsuario getInstance() {
        if (_unicaInstancia == null) {
            try {
                _unicaInstancia = new SQLiteUsuario();
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteUsuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLiteUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _unicaInstancia;
    }

    @Override
    public void insertPessoa(Usuario pessoa) {
        int admin = 0;
        if (pessoa.isAdministrador()) {
            admin = 1;
        }

        try {

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO USUARIO (NOME, EMAIL, TELEFONE, LOGIN, PASSWORD, ADMINISTRADOR) VALUES ("
                    + "'" + pessoa.getNome() + "'" + ", "
                    + "'" + pessoa.getEmail() + "'" + ", "
                    + "'" + pessoa.getTelefone() + "'" + ", "
                    + "'" + pessoa.getLogin() + "'" + ", "
                    + "'" + pessoa.getSenha() + "'" + ", "
                    + "'" + admin + "'" + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deletePessoa(Usuario pessoa) {

        int COD_USUARIO = 0;
        ResultSet rs;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE "
                    + "NOME = '" + pessoa.getNome() + "' AND "
                    + "EMAIL = '" + pessoa.getEmail() + "' AND "
                    + "LOGIN = '" + pessoa.getLogin() + "' AND "
                    + "TELEFONE = '" + pessoa.getTelefone() + "'");

            COD_USUARIO = rs.getInt("COD_USUARIO");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM USUARIO WHERE "
                    + "COD_USUARIO = '" + COD_USUARIO + "'");

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM PERMISSAO WHERE "
                    + "COD_USUARIO = '" + COD_USUARIO + "'");

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM NOTIFICACAO WHERE "
                    + "COD_PARA = '" + COD_USUARIO + "'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int quantidadePessoa() {
        ResultSet rs;
        int qtd = 0;

        try {
            rs = this.stm.executeQuery("SELECT COUNT(*) AS QUANTIDADE FROM USUARIO");

            qtd = rs.getInt("QUANTIDADE");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qtd;
    }

    @Override
    public Usuario getUser(String nome, String password) {
        Usuario p = null;
        ResultSet rs;

        try {
            rs = this.stm.executeQuery("SELECT * FROM USUARIO WHERE LOGIN = '" + nome + "' AND PASSWORD = '" + password + "'");

            p = new Usuario(rs.getString("NOME"), rs.getString("EMAIL"), rs.getString("TELEFONE"), rs.getString("LOGIN"), rs.getString("PASSWORD"), rs.getBoolean("ADMINISTRADOR"));

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    @Override
    public ArrayList<Usuario> getAllUsers() {
        ArrayList<Usuario> array = new ArrayList<Usuario>();
        ResultSet rs;

        try {
            rs = this.stm.executeQuery("SELECT * FROM USUARIO");

            while (rs.next()) {

                array.add(new Usuario(rs.getString("NOME"), rs.getString("EMAIL"), rs.getString("TELEFONE"), rs.getString("LOGIN"), rs.getString("PASSWORD"), rs.getBoolean("ADMINISTRADOR")));

            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    @Override
    public boolean getUsuario(String nome, String password) {
        ResultSet rs;
        boolean token = false;
        int cont = 0;

        try {
            rs = this.stm.executeQuery("SELECT COUNT(COD_USUARIO) AS TOKEN FROM USUARIO WHERE LOGIN = '" + nome + "' AND PASSWORD = '" + password + "'");

            cont = rs.getInt("TOKEN");
            if (cont > 0) {
                token = true;
            } else {
                token = false;
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return token;
    }

}
