package gui;

import controller.Controller;
import enumeration.Ruolo;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationFrame extends JFrame {
    private Controller controller;
    private JTextField emailField;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField telefonoField;
    private JPasswordField passwordField;
    private JComboBox ruoloBox;
    private JButton registrationButton;
    private JLabel linkLabel;

    public RegistrationFrame () {
        this.controller = new Controller();

        initLookAndFeel();
        setTitle("🍽 Gestionale Ristorante");
        setSize(350, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);

        initComponents();
        createForm();
        loadData();
        initActions();


        setVisible(true);
    }

    private void initLookAndFeel() {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
    }

    private JPanel createHeader () {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground((new Color(44, 62, 80)));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10 ,15));

        JLabel title = new JLabel("Registrati");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel subtitle = new JLabel("Sistema gestione clienti, ordini e prenotazioni");
        subtitle.setForeground(new Color(189,195,199));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel text = new JPanel(new GridLayout(2, 1));
        text.setOpaque(false);
        text.add(title);
        text.add(subtitle);

        header.add(text, BorderLayout.WEST);

        return header;
    }

    private void createForm(){
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        addRow(form, c, y++, "Nome:", nomeField);
        addRow(form, c, y++, "Cognome:", cognomeField);
        addRow(form, c, y++, "Telefono:", telefonoField);
        addRow(form, c, y++, "Email:", emailField);
        addRow(form, c, y++, "Password", passwordField);
        addRow(form, c, y++, "Ruolo:", ruoloBox);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setBackground(Color.WHITE);
        buttons.add(registrationButton);

        JPanel link = new JPanel(new FlowLayout(FlowLayout.CENTER));
        link.setBackground(Color.WHITE);
        link.add(linkLabel);

        c.gridx = 0;
        c.gridy = y++;
        c.gridwidth = 2;
        form.add(buttons, c);

        c.gridy = y;
        form.add(link, c);

        add(form, BorderLayout.CENTER);
    }

    private void initComponents() {
        nomeField = new JTextField();
        cognomeField = new JTextField();
        telefonoField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        ruoloBox = new JComboBox<>();

        registrationButton = new JButton("Registrati");
        linkLabel = new JLabel("<html><u>Effettua il login ora...</u></html>");
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        styleButton(registrationButton, new Color(46, 204, 113));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    private void initActions(){
        registrationButton.addActionListener(e-> {
            int index = ruoloBox.getSelectedIndex() -1;

            if(index <0){
                JOptionPane.showMessageDialog(this, "Seleziona un ruolo");
                return;
            }

            Ruolo ruolo = controller.getRuoli().get(index);
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (nome.isEmpty() || cognome.isEmpty() ||
                    telefono.isEmpty() || email.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Compila tutti i campi!");
                return;
            }

            Cliente c = controller.aggiungiCliente(nome, cognome, telefono, email, ruolo, password);
            if(c== null){
                JOptionPane.showMessageDialog(this, "Email già registrata riprova!");
                return;
            }

            clearFields();
            new MainFrame(controller, c.getRuolo());
            dispose();

        });

        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                new LoginFrame();
                dispose();
            }
        });
    }

    private void loadData() {
        ruoloBox.addItem("Seleziona ruolo");

        for(Ruolo r : controller.getRuoli()){
            ruoloBox.addItem(r);
        }
    }

    private void clearFields() {
        nomeField.setText("");
        cognomeField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }
}
