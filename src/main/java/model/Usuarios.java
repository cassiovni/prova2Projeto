package model;

import proxy_dao.SQLitePermissao;
import proxy_dao.SQLiteUsuario;
import java.util.ArrayList;

public class Usuarios {

    private static Usuarios _unicaInstancia;
    private Usuario usuarioLogado;
    private SQLiteUsuario databaseUsuarios;

    private Usuarios() {
        databaseUsuarios = SQLiteUsuario.getInstance();
    }

    public static Usuarios getInstance() {
        if (_unicaInstancia == null) {
            _unicaInstancia = new Usuarios();

        }
        return _unicaInstancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void cadastrarUsuarios(Usuario usuario) {

        databaseUsuarios.insertPessoa(usuario);
    }

    public void excluirUsuarios(Usuario usuario) {

        databaseUsuarios.deletePessoa(usuario);
    }

    public ArrayList<Usuario> buscarTodosUsuario() {

        ArrayList<Usuario> array = databaseUsuarios.getAllUsers();
        return array;
    }

    public int quantidadeUsuarios() {

        return databaseUsuarios.quantidadePessoa();
    }

    public boolean autenticarSessao(String login, String password) {

        if (databaseUsuarios.getUsuario(login, password)) {
            usuarioLogado = databaseUsuarios.getUser(login, password);
            return true;
        } else {
            return false;
        }
    }

    public Usuario getUsuario(String nome) {
        ArrayList<Usuario> lista = buscarTodosUsuario();
        for (Usuario u : lista) {
            if (u.getNome().equals(nome)) {
                return u;
            }
        }
        return null;
    }

}
