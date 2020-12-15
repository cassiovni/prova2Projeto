package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Notificacao;
import model.Notificacoes;
import model.Usuario;
import model.Usuarios;
import observer.Observer;
import view.ViewTelaPrincipal;

public class PresenterTelaPrincipal implements Observer{

    private ViewTelaPrincipal telaInicial;
    private Usuarios usuarioLogado;

    public PresenterTelaPrincipal() {
        
    }

    public void inicial() {
        Notificacoes.getInstance().registerObserver(this);
        ajustarTela();

        telaInicial.getjMenuItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (usuarioLogado.getUsuarioLogado().isAdministrador()) {
                    PresenterManterUsuario p = new PresenterManterUsuario();
                } else {
                    StringBuilder sb = new StringBuilder("O USUARIO ATUAL NAO E ADMINISTRADOR");
                    JOptionPane.showMessageDialog(telaInicial, sb.toString());
                }
            }
        });

        telaInicial.getjMenuItem2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Usuario usuario = Usuarios.getInstance().getUsuarioLogado();
                PresenterTodasImagens presenterTodasImagens = new PresenterTodasImagens(PresenterTodasImagens.STATE_VISUALIZAR, usuario);
            }
        });

        telaInicial.getJButtonNotificacao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                PresenterNotificacao presenterNotificacao = new PresenterNotificacao(telaInicial, null);
            }
        });

    }

    public void ajustarTela() {
        telaInicial = new ViewTelaPrincipal();
        telaInicial.getjTextField1().setText(usuarioLogado.getUsuarioLogado().getNome());
        if (usuarioLogado.getUsuarioLogado().isAdministrador()) {
            telaInicial.getjTextField2().setText("ADMINISTRADOR");
        } else {
            telaInicial.getjTextField2().setText("USUARIO");
        }
        
        setBotaoNotificacoes();

        telaInicial.setVisible(true);
        telaInicial.setLocationRelativeTo(null);
    }

    public void setBotaoNotificacoes() {
        int qtdNotificacoes = getQuantidadeDeNotificacoesAbertas();
        String texto = (qtdNotificacoes > 1) ? "Notificações" : "Notificação";
        telaInicial.getJButtonNotificacao().setText("<html>"
                + "<body>"
                + "<font face='Arial' color='red' size='100%'>" + qtdNotificacoes + "</font>"
                + "<font face='Arial' color='blue' size='100%'> " + texto + " </font>"
                + "</body>"
                + "</html>");
    }

    public int getQuantidadeDeNotificacoesAbertas() {
        ArrayList<Notificacao> notificacoes;
        Usuario usuario = Usuarios.getInstance().getUsuarioLogado();
        if (usuario.isAdministrador()) {
            notificacoes = Notificacoes.getInstance().getNotificacoes();
            int count = 0;
            for (Notificacao n : notificacoes) {
                if (n.getStatus().equals(Notificacao.STATUS_NOVA)) {
                    count++;
                }
            }
            return count;
        } else {
            notificacoes = Notificacoes.getInstance().getNotificacoesDoUsuarioConcluidas(usuario);
            return notificacoes.size();
        }
    }

    public void setUserSessao() {
        usuarioLogado = Usuarios.getInstance();
    }

    @Override
    public void update() {
        setBotaoNotificacoes();
    }
}
