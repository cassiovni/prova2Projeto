package model;

import proxy_dao.SQLitePermissao;
import java.util.ArrayList;
import proxy_dao.ImagemProxy;

public class Permissoes {

    private static Permissoes _unicaInstancia;

    private SQLitePermissao databasePermissoes;

    private Permissoes() {
        databasePermissoes = SQLitePermissao.getInstance();
    }

    public static Permissoes getInstance() {
        if (_unicaInstancia == null) {
            _unicaInstancia = new Permissoes();

        }
        return _unicaInstancia;
    }

    public void insertPermissaoAdministrador(Usuario pessoa) {
        if (pessoa.isAdministrador()) {
            databasePermissoes.insertPermissaoAdministrador(pessoa);
        }
    }

    public void removerAllPermissoes(Usuario pessoa) {

        databasePermissoes.removerAllPermissoes(pessoa);

    }

    public void removerPermissoesImagem(ImagemProxy imagemProxy) {
        databasePermissoes.removerPermissoesImagem(imagemProxy);
    }
    
    
    public ArrayList<Usuario> getUsuariosSemAcesso(ImagemProxy img) {
        return databasePermissoes.getUsuariosSemAcesso(img);
    }

}
