package model;

import java.math.BigDecimal;

public class RigaOrdine {
    private int id;
    private int quantita;
    private BigDecimal prezzo_unitario;

    public RigaOrdine (int quantita, BigDecimal prezzo_unitario){
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
    }

    public RigaOrdine () {}

    public int getId() {
        return id;
    }

    public int getQuantita() {
        return quantita;
    }

    public BigDecimal getPrezzo_unitario() {
        return prezzo_unitario;
    }

    public BigDecimal calcolaSubtotale() {
        return this.prezzo_unitario.multiply(BigDecimal.valueOf(this.quantita));
    }
}


