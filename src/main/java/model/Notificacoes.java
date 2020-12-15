package model;

import proxy_dao.ImagemProxy;
import proxy_dao.SQLiteNotificacao;
import java.util.ArrayList;
import observer.Observer;
import observer.Subject;

public class Notificacoes implements Subject {

    private static Notificacoes instance;
    private SQLiteNotificacao databaseNotificacoes;
    private ArrayList<Observer> observers;

    private Notificacoes() {
        observers = new ArrayList<>();
        databaseNotificacoes = SQLiteNotificacao.getInstance();
    }

    public static Notificacoes getInstance() {
        if (instance == null) {
            instance = new Notificacoes();
        }
        return instance;
    }

    public ArrayList<Notificacao> getNotificacoes() {
        return databaseNotificacoes.getAllNotificacao();
    }

    public boolean existeNotificacao(Usuario usuario, ImagemProxy imagem) {
        return databaseNotificacoes.existeNotificacao(usuario, imagem);
    }

    public boolean existeNotificacaoWithStatus(Usuario usuario, ImagemProxy imagem, String status) {
        return databaseNotificacoes.existeNotificacaStatuso(usuario, imagem, status);
    }

    public void addNotificacao(Notificacao n) {
        databaseNotificacoes.insertNotificacao(n);
        this.notifyObserver();
    }

    public void removeNotificacao(Notificacao n) {
        databaseNotificacoes.removeNotificacao(n);
        this.notifyObserver();
    }

    public ArrayList<Notificacao> getNotificacoesDoUsuarioConcluidas(Usuario usuario) {
        ArrayList<Notificacao> allNotificacoes = getNotificacoes();

        ArrayList<Notificacao> notificacoesConcluidas = new ArrayList<>();
        for (Notificacao n : allNotificacoes) {
            if ((n.getPara().getNome().equals(usuario.getNome())) && (n.getStatus().equals(Notificacao.STATUS_CONCLUIDA))) {
                notificacoesConcluidas.add(n);
            }
        }
        return notificacoesConcluidas;
    }

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (observers.contains(o)) {
            observers.remove(o);
        } else {
            System.out.println("Não foi possível remover o observador");
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
