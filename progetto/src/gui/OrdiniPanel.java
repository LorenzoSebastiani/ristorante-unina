package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        initLookAndFeel();
        initComponents();
        buildUI();
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

        buttonAdd = new JButton("➕ Aggiungi");
        buttonClose = new JButton("✔ Chiudi Ordine");

        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));

        label = new JLabel("Totale: €0.00");
        label.setFont(new Font("SansSerif", Font.BOLD, 14));

        // MENU TABLE
        String[] colonneMenu = {"Nome", "Categoria", "Prezzo", "Tipo"};
        menuModel = new DefaultTableModel(colonneMenu, 0);
        tableMenu = new JTable(menuModel);

        // ORDINE TABLE
        String[] colonneOrdini = {"Piatto", "Quantità", "Subtotale"};
        ordineModel = new DefaultTableModel(colonneOrdini, 0);
        tableOrdine = new JTable(ordineModel);

        styleTable(tableMenu);
        styleTable(tableOrdine);

        styleButton(buttonAdd, new Color(46, 204, 113));
        styleButton(buttonClose, new Color(231, 76, 60));
    }

    private void styleTable(JTable table) {
        table.setRowHeight(26);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void buildUI() {

        // TOP
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(getBackground());

        top.add(new JLabel("Prenotazione:"));
        top.add(comboBox);

        add(top, BorderLayout.NORTH);

        // CENTER
        JPanel center = new JPanel(new GridLayout(1, 2, 15, 0));
        center.setBackground(getBackground());

        center.add(wrapPanel("🍽 Menu", new JScrollPane(tableMenu)));
        center.add(wrapPanel("🧾 Ordine", new JScrollPane(tableOrdine)));

        add(center, BorderLayout.CENTER);

        // BOTTOM
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(getBackground());

        bottom.add(new JLabel("Quantità:"));
        bottom.add(spinner);
        bottom.add(buttonAdd);
        bottom.add(buttonClose);
        bottom.add(label);

        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel wrapPanel(String title, JComponent content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {

        comboBox.addItem("Seleziona prenotazione");

        controller.getPrenotazioni().forEach(p ->
                comboBox.addItem(
                        p.getOra_inizio() + " / " +
                                p.getOra_fine() + " - " +
                                p.getData() + " - Tavolo: " +
                                p.getTavolo().getNumero()
                )
        );

        for (Piatto p : controller.getPiattiDisponibili()) {
            String tipo = (p instanceof PiattoDelGiorno)
                    ? "Piatto del giorno"
                    : "Piatto fisso";

            menuModel.addRow(new Object[]{
                    p.getNome(),
                    p.getCategoria(),
                    "€ " + p.getPrezzo(),
                    tipo
            });
        }
    }

    private void initActions() {

        comboBox.addActionListener(e -> {
            ordineCorrente = controller.creaOrdine(LocalDateTime.now());
            ordineModel.setRowCount(0);
            label.setText("Totale: €0.00");
        });

        buttonAdd.addActionListener(e -> {

            int quantita = (int) spinner.getValue();

            if (ordineCorrente == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una prenotazione!");
                return;
            }

            int row = tableMenu.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un piatto!");
                return;
            }

            Piatto piatto = controller.getPiattiDisponibili().get(row);

            controller.aggiungiRigaOrdine(ordineCorrente, quantita, piatto.getPrezzo());

            BigDecimal subtotal = piatto.getPrezzo()
                    .multiply(BigDecimal.valueOf(quantita));

            ordineModel.addRow(new Object[]{
                    piatto.getNome(),
                    quantita,
                    "€ " + subtotal
            });
        });

        buttonClose.addActionListener(e -> {

            if (ordineCorrente == null) {
                JOptionPane.showMessageDialog(this, "Nessun ordine aperto!");
                return;
            }

            controller.chiudiOrdine(ordineCorrente);

            label.setText("Totale: €" + ordineCorrente.getTotale());

            ordineCorrente = null;
        });
    }
}