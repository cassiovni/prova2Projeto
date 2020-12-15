package proxy_dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Permissao;
import model.Usuario;

public class SQLitePermissao extends DAOPermissao {

    private static SQLitePermissao _unicaInstancia;

    public SQLitePermissao() throws SQLException, ClassNotFoundException {
        super();
    }

    public static SQLitePermissao getInstance() {
        if (_unicaInstancia == null) {
            try {
                _unicaInstancia = new SQLitePermissao();
            } catch (SQLException ex) {
                Logger.getLogger(SQLitePermissao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLitePermissao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _unicaInstancia;
    }

    @Override
    public void insertPermissao(Permissao permissao) {
        ResultSet rs;
        int COD_USUARIO = 0;
        int COD_IMAGEM = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + permissao.getPesssoa().getNome() + "'");

            while (rs.next()) {
                COD_USUARIO = rs.getInt("COD_USUARIO");
                if (COD_USUARIO != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + permissao.getImagem().getNome() + "'");

            while (rs.next()) {
                COD_IMAGEM = rs.getInt("COD_IMAGEM");
                if (COD_IMAGEM != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();

            this.stm.executeUpdate("INSERT INTO PERMISSAO (COD_USUARIO, COD_IMAGEM) VALUES ("
                    + "'" + COD_USUARIO + "'" + ", "
                    + "'" + COD_IMAGEM + "'" + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePermissao(Permissao permissao) {
        ResultSet rs;
        int COD_USUARIO = 0;
        int COD_IMAGEM = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + permissao.getPesssoa().getNome() + "'");

            while (rs.next()) {
                COD_USUARIO = rs.getInt("COD_USUARIO");
                if (COD_USUARIO != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + permissao.getImagem().getNome() + "'");

            while (rs.next()) {
                COD_IMAGEM = rs.getInt("COD_IMAGEM");
                if (COD_IMAGEM != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();

            this.stm.executeUpdate("DELETE FROM PERMISSAO WHERE "
                    + "COD_USUARIO = '" + COD_USUARIO + "'" + " AND "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean estaAutorizado(Permissao permissao) {
        ResultSet rs;
        boolean token = false;
        int cont = 0;
        int COD_USUARIO = 0;
        int COD_IMAGEM = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + permissao.getPesssoa().getNome() + "'");

            while (rs.next()) {
                COD_USUARIO = rs.getInt("COD_USUARIO");
                if (COD_USUARIO != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + permissao.getImagem().getNome() + "'");

            while (rs.next()) {
                COD_IMAGEM = rs.getInt("COD_IMAGEM");
                if (COD_IMAGEM != 0) {
                    break;
                }
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();

            rs = this.stm.executeQuery("SELECT COUNT(*) AS QUANTIDADE FROM PERMISSAO WHERE "
                    + "COD_USUARIO = '" + COD_USUARIO + "'" + " AND "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

            while (rs.next()) {
                cont = rs.getInt("QUANTIDADE");
                if (cont > 0) {
                    token = true;
                } else {
                    token = false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void removerAllPermissoes(Usuario pessoa) {
        ResultSet rs;
        int COD_USUARIO = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + pessoa.getNome() + "' AND " + "EMAIL = '" + pessoa.getEmail() + "' AND " + "LOGIN = '" + pessoa.getLogin() + "' AND " + "TELEFONE = '" + pessoa.getTelefone() + "'");

            COD_USUARIO = rs.getInt("COD_USUARIO");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();
            this.stm.executeUpdate("DELETE FROM PERMISSAO WHERE "
                    + "COD_USUARIO = '" + COD_USUARIO + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Usuario> getUsuariosSemAcesso(ImagemProxy img) {

        ArrayList<Usuario> array = new ArrayList<Usuario>();
        ResultSet rs;
        ResultSet rt;

        Usuario p = null;
        int COD_IMAGEM = 0;

        try {
            rt = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + img.getImagem().getNome() + "'");

            COD_IMAGEM = rt.getInt("COD_IMAGEM");

            rt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = this.stm.executeQuery("SELECT * FROM USUARIO WHERE COD_USUARIO IN (SELECT COD_USUARIO FROM USUARIO EXCEPT SELECT COD_USUARIO FROM PERMISSAO where COD_IMAGEM = '" + COD_IMAGEM + "')");

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
    public void removerPermissoesImagem(ImagemProxy imagemProxy) {
        ResultSet rs;
        int COD_IMAGEM = 0;

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM WHERE NOME = '" + imagemProxy.getImagem().getNome() + "'");
            COD_IMAGEM = rs.getInt("COD_IMAGEM");
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.stm = this.conn.createStatement();

            this.stm.executeUpdate("DELETE FROM PERMISSAO WHERE "
                    + "COD_IMAGEM = '" + COD_IMAGEM + "'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertPermissaoAdministrador(Usuario pessoa) {
        int COD_USUARIO = 0;
        ArrayList<String> array = new ArrayList<String>();
        ResultSet rs;
        ResultSet rt;

        System.out.println("entrou banco de dados");

        try {
            rs = this.stm.executeQuery("SELECT COD_USUARIO FROM USUARIO WHERE NOME = '" + pessoa.getNome() + "' AND " + "EMAIL = '" + pessoa.getEmail() + "' AND " + "LOGIN = '" + pessoa.getLogin() + "' AND " + "TELEFONE = '" + pessoa.getTelefone() + "'");

            COD_USUARIO = rs.getInt("COD_USUARIO");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            rs = this.stm.executeQuery("SELECT COD_IMAGEM FROM IMAGEM");

            while (rs.next()) {

                array.add(rs.getString("COD_IMAGEM"));

            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            for (String elemento : array) {
                this.stm = this.conn.createStatement();
                this.stm.executeUpdate("INSERT INTO PERMISSAO (COD_USUARIO, COD_IMAGEM) VALUES ("
                        + "'" + COD_USUARIO + "'" + ", "
                        + "'" + elemento + "'" + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
