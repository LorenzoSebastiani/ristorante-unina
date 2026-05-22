package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PiattoDelGiorno extends Piatto{
    private int quantita_disponibile;
    private LocalDate data_validata;

    public PiattoDelGiorno(String nome, String descrizione, BigDecimal prezzo, String categoria, int quantita_disponibile, LocalDate data_validata) {
        super(nome, descrizione, prezzo, categoria);
        this.quantita_disponibile = quantita_disponibile;
        this.data_validata = data_validata;
    }

    public PiattoDelGiorno(int quantita_disponibile, LocalDate data_validata) {
        this.quantita_disponibile = quantita_disponibile;
        this.data_validata = data_validata;
    }

    public int getQuantita_disponibile() {
        return quantita_disponibile;
    }

    public LocalDate getData_validata() {
        return data_validata;
    }

    @Override
    public boolean isDisponibile() {
        if(quantita_disponibile > 0 && data_validata == LocalDate.now()) return true;
        return false;
    }
}
