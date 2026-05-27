package controller;

import enumeration.Ruolo;
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

        tavoli.add(new Tavolo(1, 2, "interno"));
        tavoli.add(new Tavolo(2, 4, "interno"));
        tavoli.add(new Tavolo(3, 6, "esterno"));
        tavoli.add(new Tavolo(4, 4, "esterno"));


        piatti.add(new PiattoFisso("Carbonara", "Pasta con guanciale e pecorino", new BigDecimal("8.50"), "Primo"));
        piatti.add(new PiattoFisso("Bistecca alla griglia", "Taglio pregiato con contorno", new BigDecimal("14.00"), "Secondo"));
        piatti.add(new PiattoFisso("Tiramisu", "Dolce al mascarpone e caffe", new BigDecimal("4.50"), "Dolce"));

        piatti.add(new PiattoDelGiorno("Risotto ai funghi", "Risotto con porcini freschi", new BigDecimal("9.00"), "Primo", 5, LocalDate.now()));
        piatti.add(new PiattoDelGiorno("Branzino al forno", "Con verdure di stagione", new BigDecimal("12.00"), "Secondo", 3, LocalDate.now()));

        clienti.add(new Cliente("Mario", "Rossi", "3331234567", "mario.rossi@email.it", Ruolo.CLIENTE));
        clienti.add(new Cliente("Giulia", "Bianchi", "3479876543", "giulia.bianchi@email.it", Ruolo.OPERATORE));
        clienti.add(new Cliente("Lorenzo", "Sebastiani", "33333333333", "lorenzo@gmail.it", Ruolo.ADMIN));

        // recupera i tavoli già creati
        Tavolo t1 = tavoli.get(0);
        Tavolo t2 = tavoli.get(1);
        Tavolo t3 = tavoli.get(2);

        prenotazioni.add(new Prenotazione(
                LocalDate.now(),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                2, t1));

        prenotazioni.add(new Prenotazione(
                LocalDate.now(),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0),
                4, t2));

        prenotazioni.add(new Prenotazione(
                LocalDate.now().plusDays(1),
                LocalTime.of(20, 0),
                LocalTime.of(22, 0),
                6, t3));
    }



    // METODI CLIENTE
    public void aggiungiCliente(String nome, String cognome, String telefono, String email, Ruolo ruolo){
        Cliente cliente = new Cliente(nome, cognome, telefono, email, ruolo);
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

    public List<Ruolo> getRuoli() {
        List<Ruolo> ruoli = new ArrayList<>();
        for(Ruolo r : Ruolo.values()){
            ruoli.add(r);
        }

        return ruoli;
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
    public Prenotazione creaPrenotazione(LocalDate data, LocalTime ora_inizio, LocalTime ora_fine, int numero_persone, Tavolo tavolo) {
        if(isTavoloDisponibile(tavolo, data, ora_inizio, ora_fine)){
            Prenotazione prenotazione = new Prenotazione(data, ora_inizio, ora_fine, numero_persone, tavolo);
            prenotazioni.add(prenotazione);
            return prenotazione;
        }
        return null;
    }

    public void cancellaPrenotazione(Prenotazione p){
        if(p.isAttiva()) p.annulla();
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    // METODI ORDINE
    public Ordine creaOrdine(LocalDateTime data_ora){
        Ordine ordine = new Ordine (data_ora);
        ordini.add(ordine);
        return ordine;
    }

    public void aggiungiRigaOrdine(Ordine o, int quantita, BigDecimal prezzo_unitario){
        RigaOrdine riga = new RigaOrdine(quantita, prezzo_unitario);
        o.aggiungiRiga(riga);
        o.calcolaTotale();
    }

    public void chiudiOrdine(Ordine o){
        o.chiudi();
    }

    // METODI PIATTI
    public List<Piatto> getPiatti() {
        return piatti;
    }

    public List<Piatto> getPiattiDisponibili(){
        List<Piatto> piatti_disponibili= new ArrayList<>();
        for(Piatto p : piatti){
            if(p.isDisponibile()){
                piatti_disponibili.add(p);
            }
        }
        return piatti_disponibili;
    }
}
