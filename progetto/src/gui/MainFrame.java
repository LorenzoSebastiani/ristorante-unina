package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Controller controller;
    private JTabbedPane tabbedPane;

    public MainFrame() {

        this.controller = new Controller();

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

        header.add(text, BorderLayout.WEST);

        return header;
    }

    private JTabbedPane createTabs() {

        tabbedPane = new JTabbedPane();

        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(new Color(236, 240, 241));

        tabbedPane.addTab("🍽 Menù", new MenuPanel(controller));
        tabbedPane.addTab("🧾 Ordini", new OrdiniPanel(controller));
        tabbedPane.addTab("📅 Prenotazioni", new PrenotazioniPanel(controller));
        tabbedPane.addTab("👤 Clienti", new ClientiPanel(controller));

        return tabbedPane;
    }
}