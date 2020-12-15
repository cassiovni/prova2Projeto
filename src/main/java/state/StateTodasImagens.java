package state;

import presenter.PresenterTodasImagens;
import view.TelaTodasImagens;

public abstract class StateTodasImagens {

    protected PresenterTodasImagens presenterTodasImagens;
    protected TelaTodasImagens tela;

    public StateTodasImagens(PresenterTodasImagens presenterTodasImagens, TelaTodasImagens view) {
        this.presenterTodasImagens = presenterTodasImagens;
        this.tela = view;
    }

    public void visuliazarListaDeImagens() throws Exception {
        throw new Exception("Não é possível executar esta ação neste estado");
    }

    public void visualizarPermissoes() throws Exception {
        throw new Exception("Não é possível executar esta ação neste estado");
    }

}
