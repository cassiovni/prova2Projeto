package model;

public class Usuario {

    private String nome;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private boolean administrador;

    public Usuario(String nome, String email, String telefone, String login, String senha, boolean administrador) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.administrador = administrador;
    }

 
    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    

}
