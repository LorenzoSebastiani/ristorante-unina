package controller;

import enumeration.StatoPrenotazione;
import model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Cliente> clienti;
    private List<Tavolo> tavoli;
    private List<Prenotazione> prenotazioni;
    private List<Ordine> ordini;
    private List<Piatto> piatti;

    public Controller() {
        this.clienti = new ArrayList<Cliente>();
        this.tavoli = new ArrayList<Tavolo>();
        this.prenotazioni = new ArrayList<Prenotazione>();
        this.ordini = new ArrayList<Ordine>();
        this.piatti = new ArrayList<Piatto>();
    }

    // METODI CLIENTE
    public void aggiungiCliente(String nome, String cognome, String telefono, String email){
        Cliente cliente = new Cliente(nome, cognome, telefono, email);
        clienti.add(cliente);
    }

    public Cliente findByEmail(String email) {
        Cliente trovato = null;
        for(Cliente c : clienti){
            if(c.getEmail().equals(email) ){
                trovato = c;
                break;
            }
        }
        if(trovato != null) return trovato;
        return null;
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    // METODI TAVOLO

    public List<Tavolo> getTavoli() {
        return tavoli;
    }

    public boolean isTavoloDisponibile (Tavolo tavolo, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine){
        for(Prenotazione p : prenotazioni) {
            if (p.isAttiva() && p.getTavolo().equals(tavolo) && data.equals(p.getData()) && !(ora_inizio.isAfter(p.getOra_fine()) || ora_fine.isBefore(p.getOra_inizio()))) {
                return false;
            }
        }
        return true;
    }

    // METODI PRENOTAZIONE
    public void creaPrenotazione(LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, int numero_persone, Tavolo tavolo) {
        if(isTavoloDisponibile(tavolo, data, ora_inizio, ora_fine)){
            Prenotazione prenotazione = new Prenotazione(data, ora_inizio, ora_fine, numero_persone, tavolo);
            prenotazioni.add(prenotazione);
        }
    }

    public void cancellaPrenotazione(Prenotazione p){
        if(p.isAttiva()) p.annulla();
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    // METODI ORDINE
    public void creaOrdine(LocalDateTime data_ora){
        Ordine ordine = new Ordine (data_ora);
        ordini.add(ordine);
    }

    public void aggiungiRigaOrdine(Ordine o, int quantita, BigDecimal prezzo_unitario){
        RigaOrdine riga = new RigaOrdine(quantita, prezzo_unitario);
        o.aggiungiRiga(riga);
    }

    public void chiudiOrdine(Ordine o){
        o.chiudi();
    }

    // METODI PIATTI
    public List<Piatto> getPiatti() {
        return piatti;
    }

    public List<Piatto> getPiattiDisponibili(){
        List<Piatto> patti_disponibili= new ArrayList<>();
        for(Piatto p : piatti){
            if(p.isDisponibile()){
                patti_disponibili.add(p);
            }
        }
        return patti_disponibili;
    }
}
