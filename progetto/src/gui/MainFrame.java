package gui;

import controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private Controller controller;
    private JTable table;
    private JTabbedPane tabbedPane;

    public MainFrame () {
        this.controller = new Controller();
        this.table = new JTable();
        this.tabbedPane = new JTabbedPane();


        // TABLE
        setTitle("Prova");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(tabbedPane);
        setVisible(true);

        // TABBEDPANE
        tabbedPane.addTab("Menù", new MenuPanel(controller));
        tabbedPane.addTab("Ordini", new OrdiniPanel(controller));
        tabbedPane.addTab("Prenotazioni", new PrenotazioniPanel(controller));
        tabbedPane.addTab("Clienti", new ClientiPanel(controller));
    }
}
