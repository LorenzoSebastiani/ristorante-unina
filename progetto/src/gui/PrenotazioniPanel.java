package gui;

import controller.Controller;
import enumeration.StatoPrenotazione;
import model.Prenotazione;
import model.Tavolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PrenotazioniPanel extends JPanel {
    private Controller controller;
    private JTable prenotazioniTable;
    private DefaultTableModel model;
    private JComboBox<String> comboBox;
    private JTextField dataField;
    private JTextField oraInizioField;
    private JTextField oraFineField;
    private JSpinner n_persone;
    private JButton buttonPrenota;
    private JButton buttonCancella;

    public PrenotazioniPanel(Controller controller) {
        this.controller = controller;
        this.comboBox = new JComboBox<>();
        this.buttonPrenota = new JButton("Prenota");
        this.buttonCancella = new JButton("Cancella");
        this.dataField = new JTextField();
        this.oraInizioField = new JTextField();
        this.oraFineField = new JTextField();
        this.n_persone = new JSpinner();

        setLayout(new BorderLayout(10, 10));
        comboBox.addItem(null);
        controller.getTavoli().forEach(t -> comboBox.addItem(t.getNumero() + " - " + t.getCapienza() + " - " + t.getPosizione()));

        String[] colonne = {"Data", "Ora inizio", "Ora fine", "Numero persone", "Stato", "Tavolo"};
        model = new DefaultTableModel(colonne, 0);
        prenotazioniTable = new JTable(model);

        for(Prenotazione p : controller.getPrenotazioni()){
            model.addRow(new Object[]{
                    p.getData(),
                    p.getOra_inizio(),
                    p.getOra_fine(),
                    p.getNumero_persone(),
                    p.getStato(),
                    p.getTavolo().getNumero()
            });
        }

        add(new JScrollPane(prenotazioniTable), BorderLayout.CENTER);

        JPanel sudPanel = new JPanel(new GridLayout(6,2,5,5));
        sudPanel.add(new JLabel("Tavolo:"));
        sudPanel.add(comboBox);
        sudPanel.add(new JLabel("Data (yyyy-MM-dd):"));
        sudPanel.add(dataField);
        sudPanel.add(new JLabel("Ora inizio (HH:mm):"));
        sudPanel.add(oraInizioField);
        sudPanel.add(new JLabel("Ora Fine (HH:mm):"));
        sudPanel.add(oraFineField);
        sudPanel.add(new JLabel("Numero persone"));
        sudPanel.add(n_persone);
        sudPanel.add(buttonPrenota);
        sudPanel.add(buttonCancella);
        add(sudPanel, BorderLayout.SOUTH);

        buttonPrenota.addActionListener(e->{
            int index = comboBox.getSelectedIndex() - 1; // -1 perché hai il null come primo elemento
            if(index < 0) {
                System.err.println("Errore");
                return;
            }
            Tavolo tavolo = controller.getTavoli().get(index);

            if(tavolo == null){
                System.err.println("Seleziona un tavolo!");
                return;
            }

            try {
                LocalDate data = LocalDate.parse(dataField.getText());
                LocalTime oraInizio = LocalTime.parse(oraInizioField.getText());
                LocalTime oraFine = LocalTime.parse(oraFineField.getText());
                int nPersone = (int) n_persone.getValue();

                controller.creaPrenotazione(data, oraInizio, oraFine, nPersone, tavolo);

                model.addRow(new Object[]{
                        data,
                        oraInizio,
                        oraFine,
                        nPersone,
                        StatoPrenotazione.COMPLETATA,
                        tavolo.getNumero()
                });
            } catch (Exception ex) {
                System.err.println("Formato data/ora non valido!");
                return;
            }
        });

        buttonCancella.addActionListener(e->{
            int riga_selected = (int) prenotazioniTable.getSelectedRow();

            if(riga_selected == -1) {
                System.err.println("Seleziona una prenotazione!");
                return;
            }

            Prenotazione prenotazione = controller.getPrenotazioni().get(riga_selected);
            controller.cancellaPrenotazione(prenotazione);

            model.setValueAt(StatoPrenotazione.CANCELLATA, riga_selected, 4);
        });
    }
}
