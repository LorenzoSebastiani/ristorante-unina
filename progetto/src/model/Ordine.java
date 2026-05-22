package model;

import enumeration.StatoOrdine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ordine {
    private int id;
    private LocalDateTime data_ora;
    private BigDecimal totale;
    private StatoOrdine stato;
    private ArrayList<RigaOrdine> righe_ordine;

    public Ordine(LocalDateTime data_ora){
        this.data_ora = data_ora;
        this.totale = BigDecimal.valueOf(0);
        this.stato = StatoOrdine.APERTO;
        this.righe_ordine = new ArrayList<RigaOrdine>();
    }

    public Ordine () {}

    public int getId() {
        return id;
    }

    public LocalDateTime getData_ora() {
        return data_ora;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public ArrayList<RigaOrdine> getRighe_ordine() {
        return righe_ordine;
    }

    public void aggiungiRiga(RigaOrdine r) {
        this.righe_ordine.add(r);
    }

    public void rimuoviRiga(RigaOrdine r) {
        for (RigaOrdine riga : righe_ordine){
            if(riga.getId() == r.getId()){
                righe_ordine.remove(riga);
                break;
            }
        }
    }

    public void calcolaTotale () {
        BigDecimal totale_calcolato = BigDecimal.valueOf(0);
        for(RigaOrdine r : righe_ordine){
             totale_calcolato = totale_calcolato.add(r.calcolaSubtotale());
        }
    }

    public void chiudi() {
        this.stato = StatoOrdine.CHIUSO;
    }
}
