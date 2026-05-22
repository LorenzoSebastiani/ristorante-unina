package model;

import enumeration.StatoPrenotazione;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
    private int id;
    private LocalDate data;
    private LocalTime ora_inizio;
    private LocalTime ora_fine;
    private int numero_persone;
    private StatoPrenotazione stato;

    public Prenotazione (LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, int numero_persone) {
        this.data = data;
        this.ora_inizio = ora_inizio;
        this.ora_fine = ora_fine;
        this.numero_persone = numero_persone;
        this.stato = StatoPrenotazione.COMPLETATA;
    }

    public Prenotazione (){}

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra_inizio() {
        return ora_inizio;
    }

    public LocalTime getOra_fine() {
        return ora_fine;
    }

    public int getNumero_persone() {
        return numero_persone;
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public void conferma(){
        this.stato = StatoPrenotazione.CONFERMATA;
    }

    public void annulla() {
        this.stato = StatoPrenotazione.CANCELLATA;
    }

    public boolean isAttiva() {
        return this.stato != StatoPrenotazione.CANCELLATA;
    }
}
