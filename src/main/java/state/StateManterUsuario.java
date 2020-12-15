package state;

import presenter.PresenterManterUsuario;

public abstract class StateManterUsuario {

    protected PresenterManterUsuario presenter;

    public abstract void cadastrarUsuario();

    public abstract void excluirUsuario();

    public abstract void editarUsuario();

    public abstract void cancelar();

    public abstract void CarregarDados();
    
    public abstract void concederPermissao();

}
