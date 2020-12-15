package proxy_dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Notificacao;
import model.Usuario;

public class SQLiteNotificacao extends DAONotificacao {

    private static SQLiteNotificacao _unicaInstancia;

    public SQLiteNotificacao() throws SQLException, ClassNotFoundException {
        super();
    }

    public static SQLiteNotificacao getInstance() {
        if (_unicaInstancia == null) {
            try {
                _unicaInstancia = new SQLiteNotificacao();
            } catch (SQLException ex) {
                Logger.getLogger(SQLiteNotificacao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLiteNotificacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _unicaInstancia;
    }

    @Override
    public void insertNotificacao(Notificacao notificacao) {
        ResultSet rs;
        int COD_PARA = 0;
        int COD_IMAGEM = 0;

        try {

            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + notificacao.getPara().getNome() + "'");

            COD_PARA = rs.getInt("COD_USUARIO");

            rs.close();

            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + notificacao.getImg().getImagem().getNome() + "'");

            COD_IMAGEM = rs.getInt("COD_IMAGEM");

            rs.close();

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("INSERT INTO NOTIFICACAO (TIPO, COD_PARA, COD_IMAGEM, STATUS) VALUES ("
                    + "'" + notificacao.getTipo() + "'" + ", "
                    + "'" + COD_PARA + "'" + ", "
                    + "'" + COD_IMAGEM + "'" + ", "
                    + "'" + notificacao.getStatus() + "'" + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeNotificacao(Notificacao notificacao) {
        ResultSet rs;
        int COD_PARA = 0;
        int COD_IMAGEM = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + notificacao.getPara().getNome() + "'");

            COD_PARA = rs.getInt("COD_USUARIO");

            rs.close();

            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + notificacao.getImg().getImagem().getNome() + "'");

            COD_IMAGEM = rs.getInt("COD_IMAGEM");

            rs.close();

            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM NOTIFICACAO WHERE "
                    + "COD_PARA = '" + COD_PARA + "' AND "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Notificacao> getAllNotificacao() {
        ArrayList<Notificacao> array = new ArrayList<Notificacao>();
        ResultSet rs;
        ResultSet rt;
        int COD_PARA = 0;
        int COD_IMAGEM = 0;
        String TIPO = "";
        String STATUS = "";

        try {
            rs = this.stm.executeQuery("SELECT A.TIPO AS TIPO, B.NOME AS NOME, B.EMAIL AS EMAIL, B.TELEFONE AS TELEFONE, B.LOGIN AS LOGIN, B.PASSWORD AS PASSWORD, B.ADMINISTRADOR AS ADMINISTRADOR, C.NOME AS NOME_IMAGEM, C.DIRETORIO AS DIRETORIO, A.STATUS AS STATUS  FROM NOTIFICACAO A JOIN USUARIO B  ON  (A.COD_PARA = B.COD_USUARIO) JOIN  IMAGEM C  ON A.COD_IMAGEM =  C.COD_IMAGEM");

            while (rs.next()) {
                Notificacao novaNotificacao = new Notificacao(rs.getString("TIPO"),
                        new Usuario(rs.getString("NOME"), rs.getString("EMAIL"), rs.getString("TELEFONE"), rs.getString("LOGIN"), rs.getString("PASSWORD"), rs.getBoolean("ADMINISTRADOR")),
                        new ImagemProxy(new Imagem(rs.getString("NOME_IMAGEM"), rs.getString("DIRETORIO"))), rs.getString("STATUS"));
                novaNotificacao.setNomeImagem(rs.getString("NOME_IMAGEM"));
                array.add(novaNotificacao);

            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return array;
    }

    @Override
    public boolean existeNotificacaStatuso(Usuario usuario, ImagemProxy imagemProxy, String status) {
        ResultSet rs;
        int COD_PARA = 0;
        int COD_IMAGEM = 0;
        int QUANTIDADE = 0;
        boolean tipo = false;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + usuario.getNome() + "'");

            COD_PARA = rs.getInt("COD_USUARIO");

            rs.close();

            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + imagemProxy.getImagem().getNome() + "'");

            COD_IMAGEM = rs.getInt("COD_IMAGEM");

            rs.close();

            rs = this.stm.executeQuery("SELECT COUNT(*) AS QUANTIDADE FROM NOTIFICACAO WHERE COD_PARA = '" + COD_PARA
                    + "' AND STATUS = '" + status
                    + "' AND COD_IMAGEM = '" + COD_IMAGEM + "'");

            QUANTIDADE = rs.getInt("QUANTIDADE");
            if (QUANTIDADE != 0) {
                tipo = true;
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipo;

    }

    @Override
    public boolean existeNotificacao(Usuario usuario, ImagemProxy imagemProxy) {
        ResultSet rs;
        int COD_PARA = 0;
        int COD_IMAGEM = 0;
        int QUANTIDADE = 0;
        boolean tipo = false;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + usuario.getNome() + "'");

            COD_PARA = rs.getInt("COD_USUARIO");

            rs.close();

            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + imagemProxy.getImagem().getNome() + "'");

            COD_IMAGEM = rs.getInt("COD_IMAGEM");

            rs.close();

            rs = this.stm.executeQuery("SELECT COUNT(*) AS QUANTIDADE FROM NOTIFICACAO WHERE COD_PARA = '" + COD_PARA
                    + "' AND COD_IMAGEM = '" + COD_IMAGEM + "'");

            QUANTIDADE = rs.getInt("QUANTIDADE");
            if (QUANTIDADE != 0) {
                tipo = true;
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipo;
    }

}
