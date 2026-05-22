package model;

import java.math.BigDecimal;

public abstract class Piatto {
    protected int id;
    protected String nome;
    protected String descrizione;
    protected BigDecimal prezzo;
    protected String categoria;

    protected Piatto(String nome, String descrizione, BigDecimal prezzo, String categoria){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
    }

    protected Piatto() {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public abstract boolean isDisponibile();

    public String toString() {
        return "Piatto: " + "{" + "\'" +
                "id=" + getId() + "\'" +
                "nome=" + getNome() + "\'" +
                "descrizione=" + getDescrizione() + "\'" +
                "prezzo=" + getPrezzo() + "\'" +
                "categoria=" + getCategoria() + "\'" +
                "}";
    }
}
