package model;

import java.math.BigDecimal;

public class PiattoFisso extends Piatto{

    public PiattoFisso(String nome, String descrizione, BigDecimal prezzo, String categoria) {
        super(nome, descrizione, prezzo, categoria);
    }

    public PiattoFisso() {
    }

    @Override
    public boolean isDisponibile() {
        return true;
    }
}
