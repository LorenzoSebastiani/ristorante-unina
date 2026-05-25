package gui;

import controller.Controller;
import model.Ordine;
import model.Piatto;
import model.PiattoDelGiorno;
import model.Prenotazione;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdiniPanel extends JPanel {
    private Controller controller;
    private JComboBox<String> comboBox;
    private JTable tableMenu;
    private JTable tableOrdine;
    private JSpinner spinner;
    private JButton buttonAdd;
    private JButton buttonClose;
    private JLabel label;
    private DefaultTableModel menuModel;
    private DefaultTableModel ordineModel;
    private Ordine ordineCorrente;

    public OrdiniPanel(Controller controller) {
        this.controller = controller;
        this.buttonAdd = new JButton("Aggiungi");
        this.buttonClose = new JButton("Chiudi Ordine");
        this.comboBox = new JComboBox<>();
        this.tableMenu = new JTable();
        this.tableOrdine = new JTable();
        this.label = new JLabel();
        this.spinner = new JSpinner();

        setLayout(new BorderLayout(10, 10));
        comboBox.addItem(null);
        controller.getPrenotazioni().forEach(p -> comboBox.addItem(p.getId() + " - " + p.getData() + " - " + p.getTavolo().getNumero()));

        // TABLE
        String[] colonneMenu = {"Nome", "Categoria", "Prezzo", "Tipo"};
        menuModel = new DefaultTableModel(colonneMenu, 0);
        tableMenu = new JTable(menuModel);

        String[] colonneOrdini = {"Piatto", "Quantità", "Subtotale"};
        ordineModel = new DefaultTableModel(colonneOrdini, 0);
        tableOrdine = new JTable(ordineModel);

        comboBox.addActionListener(e -> {
            ordineCorrente = controller.creaOrdine(LocalDateTime.now());
            ordineModel.setRowCount(0);
        });

        for(Piatto p : controller.getPiattiDisponibili()){
            String tipo = null;

            if(p instanceof PiattoDelGiorno){
                tipo = "Piatto del giorno";
            } else {
                tipo = "Piatto fisso";
            }

            menuModel.addRow(new Object[]{
                    p.getNome(),
                    p.getCategoria(),
                    p.getPrezzo(),
                    tipo
            });
        }

        // BUTTON
        buttonAdd.addActionListener(e -> {
            int quantita = (int)spinner.getValue();

            if(quantita <1){
                System.err.println("Errore: Quantità deve essere maggiore di 0");
                return;
            }

            if(ordineCorrente == null){
                System.err.println("Errore: Selezionare almeno un piatto");
                return;
            }

            int rigaSelezionata = tableMenu.getSelectedRow();

            if(rigaSelezionata == -1) {
                System.err.println("Seleziona un piatto!");
                return;
            }

            Piatto piatto = controller.getPiattiDisponibili().get(rigaSelezionata);
            controller.aggiungiRigaOrdine(ordineCorrente, quantita, piatto.getPrezzo());

            ordineModel.addRow(new Object[]{
                    piatto.getNome(),
                    quantita,
                    piatto.getPrezzo().multiply(BigDecimal.valueOf(quantita))
            });
        });

        buttonClose.addActionListener(e -> {
            if(ordineCorrente == null) {
                System.err.println("Nessun ordine aperto!");
                return;
            }
            controller.chiudiOrdine(ordineCorrente);
            label.setText("Totale: €" + ordineCorrente.getTotale());
            ordineCorrente = null;
        });

        // HEADER
        JPanel nordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nordPanel.add(new JLabel("Prenotazione:"));
        nordPanel.add(comboBox);
        add(nordPanel, BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel(new GridLayout(1,2,10,0));
        centerPanel.add(new JScrollPane(tableMenu));
        centerPanel.add(new JScrollPane(tableOrdine));
        add(centerPanel, BorderLayout.CENTER);

        // FOOTER
        JPanel sudPanel = new JPanel(new FlowLayout());
        sudPanel.add(new JLabel("Quantità:"));
        sudPanel.add(spinner);
        sudPanel.add(buttonAdd);
        sudPanel.add(buttonClose);
        sudPanel.add(label);
        add(sudPanel, BorderLayout.SOUTH);

    }
}
