package gui;

import controller.Controller;
import model.Piatto;
import model.PiattoDelGiorno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuPanel extends JPanel {

    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public MenuPanel(Controller controller) {

        this.controller = controller;

        initLookAndFeel();

        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        initTable();
        loadData();
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
    }

    private void initTable() {

        String[] colonne = {
                "Nome", "Categoria", "Prezzo",
                "Tipo", "Disponibile"
        };

        model = new DefaultTableModel(colonne, 0);
        table = new JTable(model);

        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder("🍽 Menu Ristorante"));
        container.setBackground(Color.WHITE);
        container.add(scroll, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
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

        // Renderer per colonne (centratura + colori)
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(2).setCellRenderer(center); // prezzo
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);
    }

    private void loadData() {

        for (Piatto p : controller.getPiatti()) {

            String tipo = (p instanceof PiattoDelGiorno)
                    ? "⭐ Giorno"
                    : "📌 Fisso";

            String disponibile = p.isDisponibile()
                    ? "✔ Sì"
                    : "❌ No";

            String prezzo = "€ " + p.getPrezzo();

            model.addRow(new Object[]{
                    p.getNome(),
                    p.getCategoria(),
                    prezzo,
                    tipo,
                    disponibile
            });
        }
    }
}