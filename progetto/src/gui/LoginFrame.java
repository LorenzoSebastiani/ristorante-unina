package gui;

import controller.Controller;
import model.Cliente;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private Controller controller;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame () {
        this.controller = new Controller();

        initLookAndFeel();
        setTitle("🍽 Gestionale Ristorante");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);

        initComponents();
        createForm();
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

        JLabel title = new JLabel("Login");
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
        form.setBorder(BorderFactory.createTitledBorder("Inserisci Email e Password"));
        form.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        addRow(form, c, y++, "Email:", emailField);
        addRow(form, c, y++, "Password:", passwordField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        buttons.add(loginButton);

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        form.add(buttons, c);

        add(form, BorderLayout.CENTER);
    }

    private void initComponents() {
        emailField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");

        styleButton(loginButton, new Color(46, 204, 113));
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
        loginButton.addActionListener(e-> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Inserisci email e password!");
                return;
            }

            Cliente c = controller.login(email, password);
            if(c == null){
                JOptionPane.showMessageDialog(this, "Cliente non trovato... Riprova!");
                return;
            }

            new MainFrame(controller, c.getRuolo());
            dispose();
        });
    }
}
