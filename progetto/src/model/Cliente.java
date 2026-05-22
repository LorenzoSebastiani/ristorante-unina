package model;

public class Cliente {
    private int id;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;

    public Cliente ( String nome, String cognome, String telefono, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
    }

    public Cliente () {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return "Cliente: " + "{" + "\'" +
                "id=" + getId() + "\'" +
                "nome=" + getNome() + "\'" +
                "cognome=" + getCognome() + "\'" +
                "telefono=" + getTelefono() + "\'" +
                "email=" + getEmail() + "\'" +
                "}";
    }
}
