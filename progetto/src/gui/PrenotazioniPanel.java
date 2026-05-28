package gui;

import controller.Controller;
import enumeration.Ruolo;
import enumeration.StatoPrenotazione;
import model.Prenotazione;
import model.Tavolo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        initLookAndFeel();

        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        initComponents();
        buildTable();
        buildForm();
        loadData();
        initActions();
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
    }

    private void initComponents() {

        comboBox = new JComboBox<>();

        dataField = new JTextField();
        oraInizioField = new JTextField();
        oraFineField = new JTextField();

        n_persone = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));

        buttonPrenota = new JButton("📅 Prenota");
        buttonCancella = new JButton("❌ Cancella");

        styleButton(buttonPrenota, new Color(46, 204, 113));
        styleButton(buttonCancella, new Color(231, 76, 60));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void buildTable() {

        String[] colonne = {
                "Data", "Ora Inizio", "Ora Fine",
                "Persone", "Stato", "Tavolo"
        };

        model = new DefaultTableModel(colonne, 0);
        prenotazioniTable = new JTable(model);

        prenotazioniTable.setRowHeight(26);
        prenotazioniTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        prenotazioniTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        prenotazioniTable.getTableHeader().setBackground(new Color(52, 73, 94));
        prenotazioniTable.getTableHeader().setForeground(Color.WHITE);
        prenotazioniTable.setSelectionBackground(new Color(52, 152, 219));
        prenotazioniTable.setSelectionForeground(Color.WHITE);
        prenotazioniTable.setShowGrid(false);

        if(controller.getActiveCliente().getRuolo() != Ruolo.CLIENTE){
            add(new JScrollPane(prenotazioniTable), BorderLayout.CENTER);
        }
    }

    private void buildForm() {

        comboBox.addItem("Seleziona tavolo");

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        addRow(form, c, y++, "Tavolo:", comboBox);
        addRow(form, c, y++, "Data (yyyy-MM-dd):", dataField);
        addRow(form, c, y++, "Ora inizio (HH:mm):", oraInizioField);
        addRow(form, c, y++, "Ora fine (HH:mm):", oraFineField);
        addRow(form, c, y++, "Numero persone:", n_persone);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        buttons.add(buttonPrenota);
        buttons.add(buttonCancella);

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        form.add(buttons, c);

        if(controller.getActiveCliente().getRuolo() != Ruolo.CLIENTE){
            add(form, BorderLayout.SOUTH);
            return;
        }
        add(form, BorderLayout.CENTER);
    }

    private void addRow(JPanel panel, GridBagConstraints c, int y, String label, JComponent field) {

        c.gridx = 0;
        c.gridy = y;
        c.weightx = 0;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.weightx = 1;
        panel.add(field, c);
    }

    private void loadData() {

        controller.getTavoli().forEach(t ->
                comboBox.addItem(
                        t.getNumero() + " - cap: " +
                                t.getCapienza() + " - " +
                                t.getPosizione()
                )
        );

        for (Prenotazione p : controller.getPrenotazioni()) {
            model.addRow(new Object[]{
                    p.getData(),
                    p.getOra_inizio(),
                    p.getOra_fine(),
                    p.getNumero_persone(),
                    p.getStato(),
                    p.getTavolo().getNumero()
            });
        }
    }

    private void initActions() {

        buttonPrenota.addActionListener(e -> {

            int index = comboBox.getSelectedIndex() - 1;

            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Seleziona un tavolo!");
                return;
            }

            Tavolo tavolo = controller.getTavoli().get(index);

            try {
                LocalDate data = LocalDate.parse(dataField.getText());
                LocalTime inizio = LocalTime.parse(oraInizioField.getText());
                LocalTime fine = LocalTime.parse(oraFineField.getText());
                int persone = (int) n_persone.getValue();

                Prenotazione p = controller.creaPrenotazione(
                        data, inizio, fine, persone, tavolo
                );

                if(p == null){
                    JOptionPane.showMessageDialog(this, "Tavolo non disponibile in questa fascia oraria.");
                    return;
                }

                model.addRow(new Object[]{
                        data,
                        inizio,
                        fine,
                        persone,
                        StatoPrenotazione.COMPLETATA,
                        tavolo.getNumero()
                });

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Formato data/ora non valido!\nUsa yyyy-MM-dd e HH:mm");
            }
        });

        buttonCancella.addActionListener(e -> {

            int row = prenotazioniTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una prenotazione!");
                return;
            }

            Prenotazione p = controller.getPrenotazioni().get(row);
            controller.cancellaPrenotazione(p);

            model.setValueAt(StatoPrenotazione.CANCELLATA, row, 4);
        });
    }
}