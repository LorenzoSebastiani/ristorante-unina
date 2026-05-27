package gui;

import controller.Controller;
import enumeration.Ruolo;
import model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientiPanel extends JPanel {

    private Controller controller;

    private JTable table;
    private DefaultTableModel model;

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JComboBox<Ruolo> ruoloBox;

    private JButton aggiungiBtn;
    private JButton cercaButton;

    public ClientiPanel(Controller controller) {

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

        nomeField = new JTextField();
        cognomeField = new JTextField();
        telefonoField = new JTextField();
        emailField = new JTextField();
        ruoloBox = new JComboBox<>(Ruolo.values());

        aggiungiBtn = new JButton("➕ Aggiungi cliente");
        cercaButton = new JButton("🔎 Cerca email");

        styleButton(aggiungiBtn, new Color(46, 204, 113));
        styleButton(cercaButton, new Color(52, 152, 219));
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

        String[] colonne = {"Nome", "Cognome", "Telefono", "Email", "Ruolo"};

        model = new DefaultTableModel(colonne, 0);
        table = new JTable(model);

        table.setRowHeight(26);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void buildForm() {


        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Gestione Clienti"));
        form.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        addRow(form, c, y++, "Nome:", nomeField);
        addRow(form, c, y++, "Cognome:", cognomeField);
        addRow(form, c, y++, "Telefono:", telefonoField);
        addRow(form, c, y++, "Email:", emailField);
        addRow(form, c, y++, "Ruolo:", ruoloBox);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        buttons.add(aggiungiBtn);
        buttons.add(cercaButton);

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        form.add(buttons, c);

        add(form, BorderLayout.SOUTH);
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

        controller.getRuoli().forEach(r -> ruoloBox.addItem(r));

        for (Cliente c : controller.getClienti()) {
            model.addRow(new Object[]{
                    c.getNome(),
                    c.getCognome(),
                    c.getTelefono(),
                    c.getEmail()
            });
        }
    }

    private void initActions() {

        aggiungiBtn.addActionListener(e -> {

            int index = ruoloBox.getSelectedIndex() - 1;

            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Seleziona un ruolo!");
                return;
            }

            Ruolo ruolo = controller.getRuoli().get(index);

            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String email = emailField.getText().trim();

            if (nome.isEmpty() || cognome.isEmpty() ||
                    telefono.isEmpty() || email.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Compila tutti i campi!");
                return;
            }

            controller.aggiungiCliente(nome, cognome, telefono, email, ruolo);

            model.addRow(new Object[]{
                    nome, cognome, telefono, email, ruolo
            });

            clearFields();
        });

        cercaButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(
                    this,
                    "Inserisci email:"
            );

            if (email == null || email.trim().isEmpty()) return;

            Cliente trovato = controller.findByEmail(email.trim());

            if (trovato != null) {
                JOptionPane.showMessageDialog(this,
                        "Cliente trovato:\n" +
                                trovato.getNome() + " " + trovato.getCognome());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nessun cliente trovato!");
            }
        });
    }

    private void clearFields() {
        nomeField.setText("");
        cognomeField.setText("");
        telefonoField.setText("");
        emailField.setText("");
    }
}