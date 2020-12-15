package proxy_dao;

import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuarios;

public class SQLiteImagem extends DAOImagem {

    private static SQLiteImagem _unicaInstancia;

    public SQLiteImagem() throws SQLException, ClassNotFoundException {
        super();
    }

    public static SQLiteImagem getInstance() {
        if (_unicaInstancia == null) {
            try {
                _unicaInstancia = new SQLiteImagem();
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteImagem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLiteImagem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _unicaInstancia;
    }

    @Override
    public void insertImagem(Imagem imagem) {
        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO IMAGEM (NOME, DIRETORIO) VALUES ("
                    + "'" + imagem.getNome() + "'" + ", "
                    + "'" + imagem.getDiretorio() + "'" + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteImagem(ImagemProxy imagemProxy) {

        int COD_IMAGEM = 0;
        ResultSet rs;

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE "
                    + "NOME = '" + imagemProxy.getNome(Usuarios.getInstance().getUsuarioLogado()) + "' AND "
                    + "DIRETORIO = '" + imagemProxy.getDiretorio(Usuarios.getInstance().getUsuarioLogado()) + "'");

            COD_IMAGEM = rs.getInt("COD_IMAGEM");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM IMAGEM WHERE "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM PERMISSAO WHERE "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM NOTIFICACAO WHERE "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public HashMap<String, String> getImagens() {
        HashMap<String, String> map = new HashMap<String, String>();
        ResultSet rs;

        try {
            rs = this.stm.executeQuery("SELECT NOME, DIRETORIO FROM IMAGEM");

            while (rs.next()) {
                map.put(rs.getString("NOME"), rs.getString("DIRETORIO"));

            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public ImagemProxy getImagem(String nome) {
        ResultSet rs;
        ImagemProxy imagem = null;
        try {
            rs = this.stm.executeQuery("SELECT NOME, DIRETORIO FROM IMAGEM WHERE NOME = '" + nome + "'");

            imagem = new ImagemProxy(new Imagem(rs.getString("NOME"), rs.getString("DIRETORIO")));

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imagem;
    }

}
