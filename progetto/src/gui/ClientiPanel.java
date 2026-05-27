package gui;

import controller.Controller;
import model.Cliente;

import javax.swing.*;
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
    private JTextField emailConfirmField;
    private JButton aggiungiBtn;
    private JButton cercaButton;
    private JButton confirmBtn;

    public ClientiPanel(Controller controller){
        this.controller = controller;
        this.nomeField = new JTextField();
        this.cognomeField = new JTextField();
        this.emailField = new JTextField();
        this.emailConfirmField = new JTextField();
        this.telefonoField = new JTextField();
        this.aggiungiBtn = new JButton("Aggiungi");
        this.cercaButton = new JButton("Cerca per email");
        this.confirmBtn = new JButton("Cerca");

        String[] colonne = {"Nome", "Cognome", "Telefono", "Email"};
        this.model = new DefaultTableModel(colonne, 0);
        this.table = new JTable(model);

        setLayout(new BorderLayout(10,10));

        for(Cliente c : controller.getClienti()){
            model.addRow((new Object[]{
                    c.getNome(),
                    c.getCognome(),
                    c.getTelefono(),
                    c.getEmail()
            }));
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Nome"));
        panel.add(nomeField);
        panel.add(new JLabel("Cognome"));
        panel.add(cognomeField);
        panel.add(new JLabel("Telefono"));
        panel.add(telefonoField);
        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(aggiungiBtn);
        panel.add(cercaButton);
        add(panel, BorderLayout.SOUTH);

        aggiungiBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String telefono = telefonoField.getText();
            String email = emailField.getText();

            if(nome.isEmpty()  || cognome.isEmpty() || telefono == null || email == null){
                System.err.println("Errore:: Dati mancanti!");
                return;
            }

            controller.aggiungiCliente(nome, cognome, telefono, email);

            model.addRow(new Object[]{
                    nome,
                    cognome,
                    telefono,
                    email
            });
        });

        cercaButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(this, "Inserisci email:");

            if(email == null || email.isEmpty()) return;

            Cliente trovato = controller.findByEmail(email);

            if(trovato != null) {
                JOptionPane.showMessageDialog(this,
                        "Cliente trovato: " + trovato.getNome() + " " + trovato.getCognome());
            } else {
                JOptionPane.showMessageDialog(this, "Nessun cliente trovato!");
            }
        });
    }
}
