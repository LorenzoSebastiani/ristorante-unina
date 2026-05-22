package model;

public class Tavolo {
    private int id;
    private int numero;
    private int capienza;
    private String posizione;

    public Tavolo(int numero, int capienza, String posizione){
        this.numero = numero;
        this.capienza = capienza;
        this.posizione = posizione;
    }

    public Tavolo () {}

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapienza() {
        return capienza;
    }

    public String getPosizione() {
        return posizione;
    }

    public String toString () {
        return "Tavolo: " + "{" + "\'" +
                "id=" + getId() + "\'" +
                "numero=" + getNumero() + "\'" +
                "capienza=" + getCapienza() + "\'" +
                "posizione=" + getPosizione() + "\'" +
                "}";
    }
}
