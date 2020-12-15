package state;

import presenter.PresenterNotificacao;
import view.TelaNotificacoes;


public abstract class StateNotificacoes {
    
    protected PresenterNotificacao presenterNotificacao;
    protected TelaNotificacoes tela;
    
    public StateNotificacoes(PresenterNotificacao presenterNotificacao, TelaNotificacoes tela) {
        this.presenterNotificacao = presenterNotificacao;
        this.tela = tela;
    }
    
    public void visuliazarListaDeImagens() throws Exception {
        throw new Exception("Não é possível executar esta ação neste estado");
    }
    
    public void visualizarPermissoes() throws Exception {
        throw new Exception("Não é possível executar esta ação neste estado");
    }
    
}
