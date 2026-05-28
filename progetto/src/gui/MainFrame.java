package gui;

import controller.Controller;
import enumeration.Ruolo;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Controller controller;
    private JTabbedPane tabbedPane;
    private Ruolo ruolo;

    public MainFrame() {

        this.controller = new Controller();
        this.ruolo = Ruolo.ADMIN;

        initLookAndFeel();

        setTitle("🍽 Gestionale Ristorante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);

        setVisible(true);
    }

    public MainFrame(Controller controller, Ruolo ruolo){
        this.controller = controller;
        this.ruolo = ruolo;

        initLookAndFeel();

        setTitle("🍽 Gestionale Ristorante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createTabs(), BorderLayout.CENTER);

        setVisible(true);
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("🍽 Gestionale Ristorante");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel subtitle = new JLabel("Sistema gestione clienti, ordini e prenotazioni");
        subtitle.setForeground(new Color(189, 195, 199));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel text = new JPanel(new GridLayout(2, 1));
        text.setOpaque(false);
        text.add(title);
        text.add(subtitle);

        JButton button = new JButton("Esci");
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        header.add(text, BorderLayout.WEST);
        header.add(button, BorderLayout.EAST);

        button.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        return header;
    }

    private JTabbedPane createTabs() {

        tabbedPane = new JTabbedPane();

        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(236, 240, 241));

        switch (ruolo){
            case ADMIN -> {
                tabbedPane.addTab("🍽 Menù", new MenuPanel(controller));
                tabbedPane.addTab("🧾 Ordini", new OrdiniPanel(controller));
                tabbedPane.addTab("📅 Prenotazioni", new PrenotazioniPanel(controller));
                tabbedPane.addTab("👤 Clienti", new ClientiPanel(controller));
            }
            case OPERATORE -> {
                tabbedPane.addTab("🧾 Ordini", new OrdiniPanel(controller));
                tabbedPane.addTab("📅 Prenotazioni", new PrenotazioniPanel(controller));
            }
            case CLIENTE -> {
                tabbedPane.addTab("🍽 Menù", new MenuPanel(controller));
                tabbedPane.addTab("📅 Prenotazioni", new PrenotazioniPanel(controller));
            }
            default -> {
                return null;
            }
        }
        return tabbedPane;
    }
}