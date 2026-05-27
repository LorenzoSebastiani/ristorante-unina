package model;

import enumeration.Ruolo;

public class Cliente {
    private int id;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private Ruolo ruolo;

    public Cliente ( String nome, String cognome, String telefono, String email, Ruolo ruolo){
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.ruolo = ruolo;
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

    public Ruolo getRuolo(){
        return ruolo;
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
